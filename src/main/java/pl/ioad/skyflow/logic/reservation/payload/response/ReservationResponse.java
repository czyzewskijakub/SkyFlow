package pl.ioad.skyflow.logic.reservation.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReservationResponse {
    @Schema(description = "Reservation operation message response", example = "Succefully booked flight")
    private String message;
}