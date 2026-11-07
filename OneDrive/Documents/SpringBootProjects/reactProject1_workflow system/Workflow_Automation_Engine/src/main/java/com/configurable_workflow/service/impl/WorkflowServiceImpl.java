package com.configurable_workflow.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.configurable_workflow.dto.ActionRequest;
import com.configurable_workflow.dto.CreateWorkflowRequest;
import com.configurable_workflow.dto.SubmitRequest;
import com.configurable_workflow.entity.Workflow;
import com.configurable_workflow.entity.WorkflowHistory;
import com.configurable_workflow.entity.WorkflowInstance;
import com.configurable_workflow.entity.WorkflowStep;
import com.configurable_workflow.entity.WorkflowTask;
import com.configurable_workflow.enums.StepType;
import com.configurable_workflow.enums.TaskStatus;
import com.configurable_workflow.enums.WorkflowStatus;
import com.configurable_workflow.repository.WorkflowHistoryRepository;
import com.configurable_workflow.repository.WorkflowInstanceRepository;
import com.configurable_workflow.repository.WorkflowRepository;
import com.configurable_workflow.repository.WorkflowStepRepository;
import com.configurable_workflow.repository.WorkflowTaskRepository;
import com.configurable_workflow.service1.WorkflowService;

@Service
@Transactional
public class WorkflowServiceImpl implements WorkflowService {

    private final WorkflowRepository workflowRepo;
    private final WorkflowStepRepository stepRepo;
    private final WorkflowInstanceRepository instanceRepo;
    private final WorkflowTaskRepository taskRepo;
    private final WorkflowHistoryRepository historyRepo;

    public WorkflowServiceImpl(
            WorkflowRepository workflowRepo,
            WorkflowStepRepository stepRepo,
            WorkflowInstanceRepository instanceRepo,
            WorkflowTaskRepository taskRepo,
            WorkflowHistoryRepository historyRepo) {

        this.workflowRepo = workflowRepo;
        this.stepRepo = stepRepo;
        this.instanceRepo = instanceRepo;
        this.taskRepo = taskRepo;
        this.historyRepo = historyRepo;
    }

    // CREATE WORKFLOW
    @Override
    public Workflow createWorkflow(CreateWorkflowRequest request) {

        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Workflow name is required");
        }

        if (request.getSteps() == null || request.getSteps().isEmpty()) {
            throw new IllegalArgumentException("At least one workflow step is required");
        }

        Workflow workflow = new Workflow();
        workflow.setName(request.getName());
        workflow.setDescription(request.getDescription());
        workflow.setPublished(false);

        Workflow savedWorkflow = workflowRepo.save(workflow);

        for (CreateWorkflowRequest.StepRequest stepRequest : request.getSteps()) {

            WorkflowStep step = new WorkflowStep();

            step.setWorkflowId(savedWorkflow.getId());
            step.setStepName(stepRequest.getStepName());
            step.setStepOrder(stepRequest.getStepOrder());
            step.setAssignedRole(stepRequest.getAssignedRole());

            step.setStepType(
                    StepType.valueOf(stepRequest.getStepType().toUpperCase()));

            step.setConditionField(stepRequest.getConditionField());
            step.setConditionOperator(stepRequest.getConditionOperator());
            step.setConditionValue(stepRequest.getConditionValue());

            stepRepo.save(step);
        }

        return savedWorkflow;
    }

    // PUBLISH WORKFLOW
    @Override
    public Workflow publishWorkflow(Long workflowId) {

        Workflow workflow = workflowRepo.findById(workflowId)
                .orElseThrow(() -> new IllegalArgumentException("Workflow not found: " + workflowId));

        List<WorkflowStep> steps = stepRepo.findByWorkflowIdOrderByStepOrderAsc(workflowId);

        if (steps.isEmpty()) {
            throw new IllegalArgumentException("Workflow must contain at least one step");
        }

        workflow.setPublished(true);

        return workflowRepo.save(workflow);
    }

    // SUBMIT EMPLOYEE REQUEST
    @Override
    public WorkflowInstance submitRequest(SubmitRequest request) {

        Workflow workflow = workflowRepo.findById(request.getWorkflowId())
                .orElseThrow(() -> new IllegalArgumentException("Workflow not found"));

        if (!workflow.isPublished()) {
            throw new IllegalArgumentException("Workflow is not published");
        }

        if (request.getAmount() == null || request.getAmount() < 0) {
            throw new IllegalArgumentException("Valid amount is required");
        }

        WorkflowInstance instance = new WorkflowInstance();

        instance.setWorkflowId(workflow.getId());
        instance.setSubmittedBy(request.getSubmittedBy());
        instance.setAmount(request.getAmount());
        instance.setCurrentStepOrder(0);
        instance.setStatus(WorkflowStatus.IN_PROGRESS);

        WorkflowInstance savedInstance = instanceRepo.save(instance);

        addHistory(savedInstance.getId(), "SUBMITTED", request.getSubmittedBy(), "Request submitted");

        routeToNextStep(savedInstance);

        return savedInstance;
    }

    // ROUTE REQUEST TO NEXT STEP
    private void routeToNextStep(WorkflowInstance instance) {

        List<WorkflowStep> steps = stepRepo.findByWorkflowIdOrderByStepOrderAsc(instance.getWorkflowId());

        boolean skipNextApproval = false;

        for (WorkflowStep step : steps) {

            if (step.getStepOrder() <= instance.getCurrentStepOrder()) {
                continue;
            }

            // Evaluate condition step
            if (step.getStepType() == StepType.CONDITION) {

                boolean conditionMet = evaluateCondition(step, instance);

                instance.setCurrentStepOrder(step.getStepOrder());

                if (!conditionMet) {
                    skipNextApproval = true;
                    addHistory(instance.getId(), "CONDITION_FALSE", "SYSTEM", step.getStepName() + " condition was not met");
                } else {
                    addHistory(instance.getId(), "CONDITION_TRUE", "SYSTEM", step.getStepName() + " condition was met");
                }

                continue;
            }

            // Skip the next approval step if condition is false
            if (skipNextApproval) {
                skipNextApproval = false;
                addHistory(instance.getId(), "STEP_SKIPPED", "SYSTEM", step.getStepName() + " skipped due to condition");
                instance.setCurrentStepOrder(step.getStepOrder());
                continue;
            }

            // Create approval task
            WorkflowTask task = new WorkflowTask();

            task.setInstanceId(instance.getId());
            task.setStepId(step.getId());
            task.setAssignedRole(step.getAssignedRole());
            task.setStatus(TaskStatus.PENDING);

            taskRepo.save(task);

            instance.setCurrentStepOrder(step.getStepOrder());
            instanceRepo.save(instance);

            addHistory(instance.getId(), "TASK_CREATED", "SYSTEM", step.getStepName());

            return;
        }

        // No more steps
        instance.setStatus(WorkflowStatus.APPROVED);
        instanceRepo.save(instance);

        addHistory(instance.getId(), "WORKFLOW_COMPLETED", "SYSTEM", "All workflow steps completed");
    }

    // EVALUATE CONFIGURED CONDITION
    private boolean evaluateCondition(WorkflowStep step, WorkflowInstance instance) {

        if (!"amount".equalsIgnoreCase(step.getConditionField())) {
            throw new IllegalArgumentException("Unsupported condition field");
        }

        if (step.getConditionValue() == null || instance.getAmount() == null) {
            return false;
        }

        double amount = instance.getAmount();
        double value = step.getConditionValue();
        String operator = step.getConditionOperator();

        if (operator == null) {
            return false;
        }

        switch (operator.toUpperCase()) {
            case "GREATER_THAN":
                return amount > value;
            case "LESS_THAN":
                return amount < value;
            case "GREATER_THAN_OR_EQUAL":
                return amount >= value;
            case "LESS_THAN_OR_EQUAL":
                return amount <= value;
            case "EQUALS":
                return Double.compare(amount, value) == 0;
            case "NOT_EQUALS":
                return Double.compare(amount, value) != 0;
            default:
                throw new IllegalArgumentException("Unsupported condition operator: " + operator);
        }
    }

    // APPROVE TASK
    @Override
    public WorkflowTask approveTask(Long taskId, ActionRequest request) {

        WorkflowTask task = taskRepo.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + taskId));

        if (task.getStatus() != TaskStatus.PENDING) {
            throw new IllegalArgumentException("Task is already completed");
        }

        task.setStatus(TaskStatus.APPROVED);
        task.setAssignedTo(request.getPerformedBy());
        task.setComments(request.getComments());

        WorkflowTask savedTask = taskRepo.save(task);

        WorkflowInstance instance = instanceRepo.findById(task.getInstanceId())
                .orElseThrow(() -> new IllegalArgumentException("Workflow instance not found"));

        addHistory(instance.getId(), "APPROVED", request.getPerformedBy(), request.getComments());

        routeToNextStep(instance);

        return savedTask;
    }

    // REJECT TASK
    @Override
    public WorkflowTask rejectTask(Long taskId, ActionRequest request) {

        WorkflowTask task = taskRepo.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + taskId));

        if (task.getStatus() != TaskStatus.PENDING) {
            throw new IllegalArgumentException("Task is already completed");
        }

        task.setStatus(TaskStatus.REJECTED);
        task.setAssignedTo(request.getPerformedBy());
        task.setComments(request.getComments());

        WorkflowTask savedTask = taskRepo.save(task);

        WorkflowInstance instance = instanceRepo.findById(task.getInstanceId())
                .orElseThrow(() -> new IllegalArgumentException("Workflow instance not found"));

        instance.setStatus(WorkflowStatus.REJECTED);
        instanceRepo.save(instance);

        addHistory(instance.getId(), "REJECTED", request.getPerformedBy(), request.getComments());

        return savedTask;
    }

    // GET PENDING TASKS BY ROLE
    @Override
    @Transactional(readOnly = true)
    public List<WorkflowTask> getPendingTasks(String role) {
        return taskRepo.findByAssignedRoleAndStatus(role, TaskStatus.PENDING);
    }

    // GET WORKFLOW HISTORY
    @Override
    @Transactional(readOnly = true)
    public List<WorkflowHistory> getHistory(Long instanceId) {
        return historyRepo.findByInstanceIdOrderByActionTimeAsc(instanceId);
    }

    // SAVE HISTORY RECORD
    private void addHistory(Long instanceId, String action, String performedBy, String comments) {
        WorkflowHistory history = new WorkflowHistory();
        history.setInstanceId(instanceId);
        history.setAction(action);
        history.setPerformedBy(performedBy);
        history.setComments(comments);
        history.setActionTime(LocalDateTime.now());
        historyRepo.save(history);
    }
}