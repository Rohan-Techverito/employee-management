package com.example.app.user;
import jakarta.validation.constraints.*;
import java.util.UUID;
import java.time.Instant;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Payload accepted by POST /users.
 *
 * Emitted as a separate type from UserUpdateRequest so MapStruct
 * can map them with different policies — create-time we want to populate
 * server-managed fields; update-time we want to ignore null values.
 */
public record UserCreateRequest(
        @NotBlank String email,
        @NotBlank String passwordHash,
        @Pattern(regexp = "^(ROLE_ADMIN)$") @NotBlank String role
) {
}
