package com.example.app.user;
import jakarta.validation.constraints.*;
import java.util.UUID;
import java.time.Instant;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Payload accepted by PUT /users/{id}.
 *
 * Distinct from UserCreateRequest so the MapStruct mapper can
 * apply NullValuePropertyMappingStrategy.IGNORE — null fields in the
 * incoming payload leave the entity's existing value untouched.
 */
public record UserUpdateRequest(
        @NotBlank String email,
        @NotBlank String passwordHash,
        @Pattern(regexp = "^(ROLE_ADMIN)$") @NotBlank String role
) {}
