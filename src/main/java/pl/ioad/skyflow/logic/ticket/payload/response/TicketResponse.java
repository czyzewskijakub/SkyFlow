package pl.ioad.skyflow.logic.ticket.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;


public record TicketResponse(
    @Schema(description = "Reservation operation message response", example = "Successfully booked flight")
    String message
) {
}