package com.configurable_workflow.entity;

import com.configurable_workflow.enums.WorkflowStatus;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "workflow_instances")
public class WorkflowInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long workflowId;

    private String submittedBy;

    private Double amount;

    private int currentStepOrder;

    @Enumerated(EnumType.STRING)
    private WorkflowStatus status;
}