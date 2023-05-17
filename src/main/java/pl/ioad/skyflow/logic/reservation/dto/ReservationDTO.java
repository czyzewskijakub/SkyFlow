package pl.ioad.skyflow.logic.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;


@Builder
public record ReservationDTO (
    @Schema(description = "ID number of flight reservation", example = "1")
    Long reservationId
) {
}
