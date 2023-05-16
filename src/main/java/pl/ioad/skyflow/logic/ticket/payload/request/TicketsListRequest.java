package pl.ioad.skyflow.logic.ticket.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
public class TicketsListRequest {
    @NonNull
    @Schema(description = "flight's id", example = "1")
    private Long id;
}
