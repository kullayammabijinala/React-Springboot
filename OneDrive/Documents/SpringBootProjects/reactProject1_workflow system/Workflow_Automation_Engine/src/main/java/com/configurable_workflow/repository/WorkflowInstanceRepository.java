
package com.configurable_workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.configurable_workflow.entity.WorkflowInstance;
@Repository
public interface WorkflowInstanceRepository
        extends JpaRepository<WorkflowInstance, Long> {
}

