package com.example.app.timeOffRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Handles {@code pending} status processing.
 *
 * Extend this class or add collaborators here to implement variant-specific
 * business rules without touching any other class (Single Responsibility + OCP).
 */
@Component
public class PendingTimeOffRequestStrategy implements TimeOffRequestProcessingStrategy {

    private static final Logger log = LoggerFactory.getLogger(PendingTimeOffRequestStrategy.class);

    @Override
    public boolean supports(String status) {
        return "pending".equals(status);
    }

    @Override
    public void execute(TimeOffRequest entity) {
        log.debug("Executing pending strategy for {} id={}", "TimeOffRequest", entity.getId());
        // TODO: add pending-specific processing here
    }
}
