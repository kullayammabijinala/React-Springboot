package com.configurable_workflow.service1;

import java.util.List;
import com.configurable_workflow.dto.ActionRequest;
import com.configurable_workflow.dto.CreateWorkflowRequest;
import com.configurable_workflow.dto.SubmitRequest;
import com.configurable_workflow.entity.Workflow;
import com.configurable_workflow.entity.WorkflowHistory;
import com.configurable_workflow.entity.WorkflowInstance;
import com.configurable_workflow.entity.WorkflowTask;

public interface WorkflowService {

    Workflow createWorkflow(CreateWorkflowRequest request);

    Workflow publishWorkflow(Long workflowId);

    WorkflowInstance submitRequest(SubmitRequest request);

    WorkflowTask approveTask(Long taskId, ActionRequest request);

    WorkflowTask rejectTask(Long taskId, ActionRequest request);

    List<WorkflowTask> getPendingTasks(String role);

    List<WorkflowHistory> getHistory(Long instanceId);
}