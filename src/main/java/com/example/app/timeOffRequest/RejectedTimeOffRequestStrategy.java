package com.example.app.timeOffRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Handles {@code rejected} status processing.
 *
 * Extend this class or add collaborators here to implement variant-specific
 * business rules without touching any other class (Single Responsibility + OCP).
 */
@Component
public class RejectedTimeOffRequestStrategy implements TimeOffRequestProcessingStrategy {

    private static final Logger log = LoggerFactory.getLogger(RejectedTimeOffRequestStrategy.class);

    @Override
    public boolean supports(String status) {
        return "rejected".equals(status);
    }

    @Override
    public void execute(TimeOffRequest entity) {
        log.debug("Executing rejected strategy for {} id={}", "TimeOffRequest", entity.getId());
        // TODO: add rejected-specific processing here
    }
}
