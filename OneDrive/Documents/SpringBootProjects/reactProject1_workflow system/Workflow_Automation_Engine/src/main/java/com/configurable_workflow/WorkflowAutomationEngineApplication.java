package com.configurable_workflow;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication(scanBasePackages = {
    "com.configurable_workflow",
    "com.configurable_workflow.controller",
    "com.configurable_workflow.service1",
    "com.configurable_workflow.service.impl",
    "com.configurable_workflow.repository",
    "com.configurable_workflow.entity"
})
@EntityScan(basePackages = "com.configurable_workflow.entity")
@EnableJpaRepositories(basePackages = "com.configurable_workflow.repository")
public class WorkflowAutomationEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkflowAutomationEngineApplication.class, args);
    }

    @Bean
    ApplicationRunner showMappings(RequestMappingHandlerMapping handlerMapping) {
        return args -> {
            System.out.println("====== REGISTERED API ENDPOINTS ======");
            handlerMapping.getHandlerMethods().forEach((key, value) -> {
                System.out.println(key + " -> " + value.getMethod().getName());
            });
            System.out.println("======================================");
        };
    }
}