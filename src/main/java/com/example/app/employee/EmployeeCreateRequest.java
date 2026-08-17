package com.example.app.employee;
import jakarta.validation.constraints.*;
import java.util.UUID;
import java.time.Instant;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Payload accepted by POST /employees.
 *
 * Emitted as a separate type from EmployeeUpdateRequest so MapStruct
 * can map them with different policies — create-time we want to populate
 * server-managed fields; update-time we want to ignore null values.
 */
public record EmployeeCreateRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String email,
        @NotBlank String jobTitle,
        @NotNull LocalDate hireDate,
        @Pattern(regexp = "^(active|inactive|terminated)$") @NotBlank String status,
        String phone,
        UUID department
) {
}
