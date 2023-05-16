package pl.ioad.skyflow.logic.reservation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
public class CancelRequest {

    @NotBlank
    @NonNull
    @Schema(description = "reservation id of flight user wants to cancel", example = "1")
    private Long reservationId;
}
