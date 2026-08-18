package com.example.app.employeeSkill;
import jakarta.validation.constraints.*;
import java.util.UUID;
import java.time.Instant;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Payload accepted by PUT /employee-skills/{id}.
 *
 * Distinct from EmployeeSkillCreateRequest so the MapStruct mapper can
 * apply NullValuePropertyMappingStrategy.IGNORE — null fields in the
 * incoming payload leave the entity's existing value untouched.
 */
public record EmployeeSkillUpdateRequest(
        @NotNull UUID employeeId,
        @NotBlank String skillName,
        @Pattern(regexp = "^(beginner|intermediate|expert)$") @NotBlank String proficiencyLevel
) {}
