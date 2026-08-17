package com.example.app.department;
import java.util.UUID;
import java.time.Instant;
import io.swagger.v3.oas.annotations.media.Schema;

public record DepartmentResponse(
        UUID id,
        String name,
        Instant createdAt,
        Instant updatedAt
) {

    public static DepartmentResponse from(Department entity) {
        return new DepartmentResponse(
entity.getId(),
entity.getName(),
entity.getCreatedAt(),
entity.getUpdatedAt()
        );
    }
}
