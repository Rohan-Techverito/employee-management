package com.example.app.user;
import java.util.UUID;
import java.time.Instant;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserResponse(
        UUID id,
        String email,
        String passwordHash,
        String role,
        Instant createdAt,
        Instant updatedAt
) {

    public static UserResponse from(User entity) {
        return new UserResponse(
entity.getId(),
entity.getEmail(),
entity.getPasswordHash(),
entity.getRole(),
entity.getCreatedAt(),
entity.getUpdatedAt()
        );
    }
}
