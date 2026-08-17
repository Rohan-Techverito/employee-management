package com.example.app.employeeDocument;
import jakarta.validation.constraints.*;
import java.util.UUID;
import java.time.Instant;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Payload accepted by POST /employee-documents.
 *
 * Emitted as a separate type from EmployeeDocumentUpdateRequest so MapStruct
 * can map them with different policies — create-time we want to populate
 * server-managed fields; update-time we want to ignore null values.
 */
public record EmployeeDocumentCreateRequest(
        @NotNull UUID employeeId,
        @NotBlank String originalFilename,
        @NotBlank String mimeType,
        @NotBlank String storagePath,
        @NotNull Long sizeBytes
) {
}
