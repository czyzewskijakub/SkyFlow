package pl.ioad.skyflow.logic.reservation.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;


public record ReservationResponse (
    @Schema(description = "Reservation operation message response", example = "Succefully booked flight")
    String message
) {
}