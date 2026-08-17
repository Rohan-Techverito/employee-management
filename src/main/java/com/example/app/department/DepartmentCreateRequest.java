package com.example.app.department;
import jakarta.validation.constraints.*;
import java.util.UUID;
import java.time.Instant;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Payload accepted by POST /departments.
 *
 * Emitted as a separate type from DepartmentUpdateRequest so MapStruct
 * can map them with different policies — create-time we want to populate
 * server-managed fields; update-time we want to ignore null values.
 */
public record DepartmentCreateRequest(
        @NotBlank String name
) {
}
