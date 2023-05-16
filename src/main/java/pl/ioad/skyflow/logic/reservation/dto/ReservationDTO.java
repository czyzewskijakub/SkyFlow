package pl.ioad.skyflow.logic.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReservationDTO {
    @Schema(description = "ID number of flight reservation", example = "1")
    private Long reservationId;
}
