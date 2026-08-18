package com.example.app.employeeSkill;
import jakarta.validation.constraints.*;
import java.util.UUID;
import java.time.Instant;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Payload accepted by POST /employee-skills.
 *
 * Emitted as a separate type from EmployeeSkillUpdateRequest so MapStruct
 * can map them with different policies — create-time we want to populate
 * server-managed fields; update-time we want to ignore null values.
 */
public record EmployeeSkillCreateRequest(
        @NotNull UUID employeeId,
        @NotBlank String skillName,
        @Pattern(regexp = "^(beginner|intermediate|expert)$") @NotBlank String proficiencyLevel
) {
}
