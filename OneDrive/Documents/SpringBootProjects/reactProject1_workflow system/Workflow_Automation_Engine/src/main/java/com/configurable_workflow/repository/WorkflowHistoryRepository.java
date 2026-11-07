
package com.configurable_workflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.configurable_workflow.entity.WorkflowHistory;

@Repository
public interface WorkflowHistoryRepository
        extends JpaRepository<WorkflowHistory, Long> {

    List<WorkflowHistory> findByInstanceIdOrderByActionTimeAsc(Long instanceId);
}
