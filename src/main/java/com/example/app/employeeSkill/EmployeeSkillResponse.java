package com.example.app.employeeSkill;
import java.util.UUID;
import java.time.Instant;
import io.swagger.v3.oas.annotations.media.Schema;

public record EmployeeSkillResponse(
        UUID id,
        UUID employeeId,
        String skillName,
        String proficiencyLevel,
        Instant createdAt,
        Instant updatedAt
) {

    public static EmployeeSkillResponse from(EmployeeSkill entity) {
        return new EmployeeSkillResponse(
entity.getId(),
entity.getEmployeeId(),
entity.getSkillName(),
entity.getProficiencyLevel(),
entity.getCreatedAt(),
entity.getUpdatedAt()
        );
    }
}
