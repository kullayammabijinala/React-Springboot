package com.configurable_workflow.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.configurable_workflow.dto.ActionRequest;
import com.configurable_workflow.entity.WorkflowHistory;
import com.configurable_workflow.entity.WorkflowTask;
import com.configurable_workflow.repository.WorkflowHistoryRepository;
import com.configurable_workflow.repository.WorkflowTaskRepository;
import com.configurable_workflow.service1.WorkflowService;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    private final WorkflowTaskRepository taskRepo;
    private final WorkflowHistoryRepository historyRepo;
    private final WorkflowService workflowService;

    public TaskController(
            WorkflowTaskRepository taskRepo,
            WorkflowHistoryRepository historyRepo,
            WorkflowService workflowService) {

        this.taskRepo = taskRepo;
        this.historyRepo = historyRepo;
        this.workflowService = workflowService;
    }

    // GET ALL TASKS
    @GetMapping
    public List<WorkflowTask> getAllTasks() {
        return taskRepo.findAll();
    }

    // GET TASK BY ID
    @GetMapping("/{id}")
    public ResponseEntity<WorkflowTask> getTaskById(
            @PathVariable Long id) {

        return taskRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST - CREATE TASK
    @PostMapping
    public WorkflowTask createTask(
            @RequestBody WorkflowTask task) {

        task.setId(null);

        return taskRepo.save(task);
    }

    // PUT - FULL UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<WorkflowTask> updateTask(
            @PathVariable Long id,
            @RequestBody WorkflowTask updatedTask) {

        return taskRepo.findById(id).map(task -> {

            task.setAssignedRole(
                    updatedTask.getAssignedRole());

            task.setAssignedTo(
                    updatedTask.getAssignedTo());

            task.setComments(
                    updatedTask.getComments());

            task.setStatus(updatedTask.getStatus());

            return ResponseEntity.ok(taskRepo.save(task));

        }).orElse(ResponseEntity.notFound().build());
    }

    // PATCH - PARTIAL UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<WorkflowTask> patchTask(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        return taskRepo.findById(id).map(task -> {

            if (updates.containsKey("assignedRole")) {
                task.setAssignedRole(
                        (String) updates.get("assignedRole"));
            }

            if (updates.containsKey("assignedTo")) {
                task.setAssignedTo(
                        (String) updates.get("assignedTo"));
            }

            if (updates.containsKey("comments")) {
                task.setComments(
                        (String) updates.get("comments"));
            }

            return ResponseEntity.ok(taskRepo.save(task));

        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE TASK
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id) {

        if (!taskRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        taskRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // GET PENDING TASKS BY ROLE
    @GetMapping("/pending/{role}")
    public List<WorkflowTask> getPendingTasks(
            @PathVariable String role) {

        return workflowService.getPendingTasks(
                role.toUpperCase());
    }

    // APPROVE TASK
    @PostMapping("/{id}/approve")
    public WorkflowTask approveTask(
            @PathVariable Long id,
            @RequestBody ActionRequest request) {

        return workflowService.approveTask(id, request);
    }

    // REJECT TASK
    @PostMapping("/{id}/reject")
    public WorkflowTask rejectTask(
            @PathVariable Long id,
            @RequestBody ActionRequest request) {

        return workflowService.rejectTask(id, request);
    }

    // GET WORKFLOW HISTORY
    @GetMapping("/history/{instanceId}")
    public List<WorkflowHistory> getHistory(
            @PathVariable Long instanceId) {

        return historyRepo
                .findByInstanceIdOrderByActionTimeAsc(instanceId);
    }
}