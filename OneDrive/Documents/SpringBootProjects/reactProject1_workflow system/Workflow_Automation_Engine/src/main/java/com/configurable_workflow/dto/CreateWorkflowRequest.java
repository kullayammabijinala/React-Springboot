
package com.configurable_workflow.dto;

import java.util.List;
import lombok.Data;

@Data
public class CreateWorkflowRequest {

    private String name;
    private String description;
    private List<StepRequest> steps;

    @Data
    public static class StepRequest {
        private String stepName;
        private Integer stepOrder;
        private String assignedRole;
        private String stepType;
        private String conditionField;
        private String conditionOperator;
        private Double conditionValue;
    }
}

