package com.example.app.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Handles {@code inactive} status processing.
 *
 * Extend this class or add collaborators here to implement variant-specific
 * business rules without touching any other class (Single Responsibility + OCP).
 */
@Component
public class InactiveEmployeeStrategy implements EmployeeProcessingStrategy {

    private static final Logger log = LoggerFactory.getLogger(InactiveEmployeeStrategy.class);

    @Override
    public boolean supports(String status) {
        return "inactive".equals(status);
    }

    @Override
    public void execute(Employee entity) {
        log.debug("Executing inactive strategy for {} id={}", "Employee", entity.getId());
        // TODO: add inactive-specific processing here
    }
}
