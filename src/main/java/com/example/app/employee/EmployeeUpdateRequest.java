package com.example.app.employee;
import jakarta.validation.constraints.*;
import java.util.UUID;
import java.time.Instant;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Payload accepted by PUT /employees/{id}.
 *
 * Distinct from EmployeeCreateRequest so the MapStruct mapper can
 * apply NullValuePropertyMappingStrategy.IGNORE — null fields in the
 * incoming payload leave the entity's existing value untouched.
 */
public record EmployeeUpdateRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String email,
        @NotBlank String jobTitle,
        @NotNull LocalDate hireDate,
        @Pattern(regexp = "^(active|inactive|terminated)$") @NotBlank String status,
        UUID department
) {}
