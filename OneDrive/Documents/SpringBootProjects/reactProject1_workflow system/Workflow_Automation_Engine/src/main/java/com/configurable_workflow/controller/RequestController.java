package com.configurable_workflow.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.configurable_workflow.dto.SubmitRequest;
import com.configurable_workflow.entity.WorkflowInstance;
import com.configurable_workflow.repository.WorkflowInstanceRepository;
import com.configurable_workflow.service1.WorkflowService;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = "http://localhost:5173")
public class RequestController {

    private final WorkflowInstanceRepository instanceRepo;
    private final WorkflowService workflowService;

    public RequestController(
            WorkflowInstanceRepository instanceRepo,
            WorkflowService workflowService) {
        this.instanceRepo = instanceRepo;
        this.workflowService = workflowService;
    }

    // GET ALL REQUESTS
    @GetMapping
    public List<WorkflowInstance> getAllRequests() {
        return instanceRepo.findAll();
    }

    // GET REQUEST BY ID
    @GetMapping("/{id}")
    public ResponseEntity<WorkflowInstance> getRequestById(
            @PathVariable Long id) {

        return instanceRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST - SUBMIT REQUEST THROUGH WORKFLOW ENGINE
    @PostMapping("/submit")
    public WorkflowInstance submitRequest(
            @RequestBody SubmitRequest request) {

        return workflowService.submitRequest(request);
    }

    // PUT - FULL UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<WorkflowInstance> updateRequest(
            @PathVariable Long id,
            @RequestBody WorkflowInstance updatedRequest) {

        return instanceRepo.findById(id).map(instance -> {

            instance.setSubmittedBy(
                    updatedRequest.getSubmittedBy());

            instance.setAmount(updatedRequest.getAmount());

            return ResponseEntity.ok(
                    instanceRepo.save(instance));

        }).orElse(ResponseEntity.notFound().build());
    }

    // PATCH - PARTIAL UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<WorkflowInstance> patchRequest(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        return instanceRepo.findById(id).map(instance -> {

            if (updates.containsKey("submittedBy")) {
                instance.setSubmittedBy(
                        (String) updates.get("submittedBy"));
            }

            if (updates.containsKey("amount")) {
                instance.setAmount(
                        Double.valueOf(
                                updates.get("amount").toString()));
            }

            return ResponseEntity.ok(
                    instanceRepo.save(instance));

        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE REQUEST
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(
            @PathVariable Long id) {

        if (!instanceRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        instanceRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}