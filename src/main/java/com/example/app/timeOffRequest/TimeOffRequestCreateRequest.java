package com.example.app.timeOffRequest;
import jakarta.validation.constraints.*;
import java.util.UUID;
import java.time.Instant;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Payload accepted by POST /time-off-requests.
 *
 * Emitted as a separate type from TimeOffRequestUpdateRequest so MapStruct
 * can map them with different policies — create-time we want to populate
 * server-managed fields; update-time we want to ignore null values.
 */
public record TimeOffRequestCreateRequest(
        @NotNull UUID employeeId,
        @NotNull UUID managerId,
        @Pattern(regexp = "^(pending|approved|rejected)$") @NotBlank String status
) {
}
