package com.example.app.timeOffRequest;
import java.util.UUID;
import java.time.Instant;
import io.swagger.v3.oas.annotations.media.Schema;

public record TimeOffRequestResponse(
        UUID id,
        UUID employeeId,
        UUID managerId,
        String status,
        Instant createdAt,
        Instant updatedAt
) {

    public static TimeOffRequestResponse from(TimeOffRequest entity) {
        return new TimeOffRequestResponse(
entity.getId(),
entity.getEmployeeId(),
entity.getManagerId(),
entity.getStatus(),
entity.getCreatedAt(),
entity.getUpdatedAt()
        );
    }
}
