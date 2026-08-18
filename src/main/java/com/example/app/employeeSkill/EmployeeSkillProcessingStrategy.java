package com.example.app.employeeSkill;

/**
 * Strategy for EmployeeSkill processing, dispatched by the {@code proficiencyLevel} field.
 *
 * Implement this interface and annotate with {@code @Component} to support a new
 * variant — no changes required in service or factory code (Open/Closed Principle).
 */
public interface EmployeeSkillProcessingStrategy {

    /** Returns true when this strategy handles the given proficiencyLevel value. */
    boolean supports(String proficiencyLevel);

    /** Apply strategy-specific logic to the entity before it is persisted. */
    void execute(EmployeeSkill entity);
}
