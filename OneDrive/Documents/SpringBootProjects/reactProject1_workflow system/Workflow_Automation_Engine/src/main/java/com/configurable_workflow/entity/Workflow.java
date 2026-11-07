package com.configurable_workflow.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "workflows")
public class Workflow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private boolean published = false;
}
