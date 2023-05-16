package pl.ioad.skyflow.logic.ticket.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
public class TicketsRequest {

    @NonNull
    @Schema(description = "Price of ticket[USD]", example = "120")
    private Double price;

    @NonNull
    @Schema(description = "Availability of ticket", example = "false")
    private Boolean availability;

    @NonNull
    @Schema(description = "ticket's seat", example = "A12")
    private String seat;

//    @NonNull
//    @Schema(description = "flight's id", example = "1")
//    private Long id;

}
