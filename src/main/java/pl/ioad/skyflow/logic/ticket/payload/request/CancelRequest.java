package pl.ioad.skyflow.logic.ticket.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public record CancelRequest (
        @NonNull
        @Schema(description = "reservation id of flight user wants to cancel", example = "1")
        Long ticketId
) {
}
