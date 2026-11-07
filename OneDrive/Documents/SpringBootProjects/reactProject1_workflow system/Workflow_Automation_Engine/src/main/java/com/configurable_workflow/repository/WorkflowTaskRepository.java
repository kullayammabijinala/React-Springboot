
package com.configurable_workflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.configurable_workflow.entity.WorkflowTask;
import com.configurable_workflow.enums.TaskStatus;
@Repository
public interface WorkflowTaskRepository
        extends JpaRepository<WorkflowTask, Long> {

    List<WorkflowTask> findByAssignedRoleAndStatus(
            String assignedRole, TaskStatus status);
}

