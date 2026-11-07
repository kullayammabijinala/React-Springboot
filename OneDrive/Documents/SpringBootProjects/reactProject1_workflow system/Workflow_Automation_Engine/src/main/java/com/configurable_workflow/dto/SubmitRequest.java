package com.configurable_workflow.dto;

public class SubmitRequest {

    private Long workflowId;
    private String submittedBy;
    private Double amount;

    // Default No-Args Constructor (Required by Jackson JSON parser)
    public SubmitRequest() {
    }

    // Parameterized Constructor
    public SubmitRequest(Long workflowId, String submittedBy, Double amount) {
        this.workflowId = workflowId;
        this.submittedBy = submittedBy;
        this.amount = amount;
    }

    // Getters and Setters
    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}