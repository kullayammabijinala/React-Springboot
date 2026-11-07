package com.configurable_workflow.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.configurable_workflow.dto.CreateWorkflowRequest;
import com.configurable_workflow.entity.Workflow;
import com.configurable_workflow.repository.WorkflowRepository;
import com.configurable_workflow.service1.WorkflowService;

@RestController
@RequestMapping("/api/workflows")
@CrossOrigin(origins = "http://localhost:5173")
public class WorkflowController {

    private final WorkflowRepository workflowRepo;
    private final WorkflowService workflowService;

    public WorkflowController(
            WorkflowRepository workflowRepo,
            WorkflowService workflowService) {
        this.workflowRepo = workflowRepo;
        this.workflowService = workflowService;
    }

    // GET ALL
    @GetMapping
    public List<Workflow> getAllWorkflows() {
        return workflowRepo.findAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Workflow> getWorkflowById(
            @PathVariable Long id) {

        return workflowRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST - CREATE
 // POST - CREATE
    @PostMapping
    public ResponseEntity<Workflow> createWorkflow(
            @RequestBody CreateWorkflowRequest request) {

        Workflow createdWorkflow = workflowService.createWorkflow(request);
        return ResponseEntity.ok(createdWorkflow);
    }

    // PUT - FULL UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Workflow> updateWorkflow(
            @PathVariable Long id,
            @RequestBody Workflow updatedWorkflow) {

        return workflowRepo.findById(id).map(workflow -> {

            workflow.setName(updatedWorkflow.getName());
            workflow.setDescription(
                    updatedWorkflow.getDescription());

            return ResponseEntity.ok(
                    workflowRepo.save(workflow));

        }).orElse(ResponseEntity.notFound().build());
    }

    // PATCH - PARTIAL UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<Workflow> patchWorkflow(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        return workflowRepo.findById(id).map(workflow -> {

            if (updates.containsKey("name")) {
                workflow.setName(
                        (String) updates.get("name"));
            }

            if (updates.containsKey("description")) {
                workflow.setDescription(
                        (String) updates.get("description"));
            }

            return ResponseEntity.ok(
                    workflowRepo.save(workflow));

        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkflow(
            @PathVariable Long id) {

        if (!workflowRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        workflowRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // PUBLISH WORKFLOW
    @PutMapping("/{id}/publish")
    public Workflow publishWorkflow(@PathVariable Long id) {
        return workflowService.publishWorkflow(id);
    }
}