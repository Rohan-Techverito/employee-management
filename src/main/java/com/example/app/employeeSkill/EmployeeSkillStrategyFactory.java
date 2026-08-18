package com.example.app.employeeSkill;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Resolves the correct {@link EmployeeSkillProcessingStrategy} at runtime.
 *
 * Uses the Null Object pattern: when no registered strategy matches, a no-op
 * implementation is returned so the application never throws on an unknown
 * variant — behaviour can be upgraded by registering a new {@code @Component}.
 */
@Component
public class EmployeeSkillStrategyFactory {

    private static final Logger log = LoggerFactory.getLogger(EmployeeSkillStrategyFactory.class);

    private final List<EmployeeSkillProcessingStrategy> strategies;

    public EmployeeSkillStrategyFactory(List<EmployeeSkillProcessingStrategy> strategies) {
        this.strategies = strategies;
    }

    public EmployeeSkillProcessingStrategy resolve(String proficiencyLevel) {
        return strategies.stream()
                .filter(s -> s.supports(proficiencyLevel))
                .findFirst()
                .orElseGet(() -> new NoOpStrategy(proficiencyLevel));
    }

    /** Null Object — logs a warning and does nothing when no strategy is registered. */
    private static final class NoOpStrategy implements EmployeeSkillProcessingStrategy {
        private final String variant;
        NoOpStrategy(String variant) { this.variant = variant; }

        @Override
        public boolean supports(String value) { return true; }

        @Override
        public void execute(EmployeeSkill entity) {
            log.warn("No EmployeeSkillProcessingStrategy registered for proficiencyLevel='{}'; applying no-op (Null Object).", variant);
        }
    }
}
