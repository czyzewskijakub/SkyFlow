package pl.ioad.skyflow.logic.ticket.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TicketResponse {
    @Schema(description = "Ticket operation message response", example = "Succefully created ticket")
    private String message;
}
