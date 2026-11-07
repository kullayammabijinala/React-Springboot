
package com.configurable_workflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.configurable_workflow.entity.WorkflowStep;
@Repository
public interface WorkflowStepRepository
        extends JpaRepository<WorkflowStep, Long> {

    List<WorkflowStep> findByWorkflowIdOrderByStepOrderAsc(Long workflowId);
}

