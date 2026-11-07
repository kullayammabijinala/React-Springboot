package com.configurable_workflow.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "workflow_history")
public class WorkflowHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long instanceId;

    private String action;

    private String performedBy;

    private String comments;

    private LocalDateTime actionTime;
}
