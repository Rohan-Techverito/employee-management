package com.example.app.timeOffRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Handles {@code approved} status processing.
 *
 * Extend this class or add collaborators here to implement variant-specific
 * business rules without touching any other class (Single Responsibility + OCP).
 */
@Component
public class ApprovedTimeOffRequestStrategy implements TimeOffRequestProcessingStrategy {

    private static final Logger log = LoggerFactory.getLogger(ApprovedTimeOffRequestStrategy.class);

    @Override
    public boolean supports(String status) {
        return "approved".equals(status);
    }

    @Override
    public void execute(TimeOffRequest entity) {
        log.debug("Executing approved strategy for {} id={}", "TimeOffRequest", entity.getId());
        // TODO: add approved-specific processing here
    }
}
