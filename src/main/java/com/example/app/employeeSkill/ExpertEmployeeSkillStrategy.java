package com.example.app.employeeSkill;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Handles {@code expert} proficiencyLevel processing.
 *
 * Extend this class or add collaborators here to implement variant-specific
 * business rules without touching any other class (Single Responsibility + OCP).
 */
@Component
public class ExpertEmployeeSkillStrategy implements EmployeeSkillProcessingStrategy {

    private static final Logger log = LoggerFactory.getLogger(ExpertEmployeeSkillStrategy.class);

    @Override
    public boolean supports(String proficiencyLevel) {
        return "expert".equals(proficiencyLevel);
    }

    @Override
    public void execute(EmployeeSkill entity) {
        log.debug("Executing expert strategy for {} id={}", "EmployeeSkill", entity.getId());
        // TODO: add expert-specific processing here
    }
}
