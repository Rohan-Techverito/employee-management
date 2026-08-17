package com.example.app.employeeDocument;
import java.util.UUID;
import java.time.Instant;
import io.swagger.v3.oas.annotations.media.Schema;

public record EmployeeDocumentResponse(
        UUID id,
        UUID employeeId,
        String originalFilename,
        String mimeType,
        String storagePath,
        Long sizeBytes,
        Instant createdAt,
        Instant updatedAt
) {

    public static EmployeeDocumentResponse from(EmployeeDocument entity) {
        return new EmployeeDocumentResponse(
entity.getId(),
entity.getEmployeeId(),
entity.getOriginalFilename(),
entity.getMimeType(),
entity.getStoragePath(),
entity.getSizeBytes(),
entity.getCreatedAt(),
entity.getUpdatedAt()
        );
    }
}
