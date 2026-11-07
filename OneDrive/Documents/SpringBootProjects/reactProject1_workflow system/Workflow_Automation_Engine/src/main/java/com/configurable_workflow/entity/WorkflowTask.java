package com.configurable_workflow.entity;

import com.configurable_workflow.enums.TaskStatus;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "workflow_tasks")
public class WorkflowTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long instanceId;

    private Long stepId;

    private String assignedRole;

    private String assignedTo;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private String comments;
}
