package com.example.app.timeOffRequest;
import jakarta.validation.constraints.*;
import java.util.UUID;
import java.time.Instant;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Payload accepted by PUT /time-off-requests/{id}.
 *
 * Distinct from TimeOffRequestCreateRequest so the MapStruct mapper can
 * apply NullValuePropertyMappingStrategy.IGNORE — null fields in the
 * incoming payload leave the entity's existing value untouched.
 */
public record TimeOffRequestUpdateRequest(
        @NotNull UUID employeeId,
        @NotNull UUID managerId,
        @Pattern(regexp = "^(pending|approved|rejected)$") @NotBlank String status
) {}
