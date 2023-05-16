package pl.ioad.skyflow.logic.reservation.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
public class CancelRequest {

    @NonNull
    @Schema(description = "reservation id of flight user wants to cancel", example = "1")
    private Long reservationId;
}
