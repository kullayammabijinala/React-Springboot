
package com.configurable_workflow.entity;

import com.configurable_workflow.enums.StepType;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "workflow_steps")
public class WorkflowStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long workflowId;

    @Column(nullable = false)
    private String stepName;

    private int stepOrder;

    private String assignedRole;

    @Enumerated(EnumType.STRING)
    private StepType stepType;

    // Condition configuration
    private String conditionField;

    private String conditionOperator;

    private Double conditionValue;
}