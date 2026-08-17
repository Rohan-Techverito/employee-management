package com.example.app.employeeDocument;
import jakarta.validation.constraints.*;
import java.util.UUID;
import java.time.Instant;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Payload accepted by PUT /employee-documents/{id}.
 *
 * Distinct from EmployeeDocumentCreateRequest so the MapStruct mapper can
 * apply NullValuePropertyMappingStrategy.IGNORE — null fields in the
 * incoming payload leave the entity's existing value untouched.
 */
public record EmployeeDocumentUpdateRequest(
        @NotNull UUID employeeId,
        @NotBlank String originalFilename,
        @NotBlank String mimeType,
        @NotBlank String storagePath,
        @NotNull Long sizeBytes
) {}
