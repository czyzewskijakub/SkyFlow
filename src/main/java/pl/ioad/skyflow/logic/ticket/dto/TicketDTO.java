package pl.ioad.skyflow.logic.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;


@Builder
public record TicketDTO(
    @Schema(description = "ID number of flight ticket", example = "1")
    Long ticketId,
    @Schema(description = "price of ticket in EUR", example = "100")
    Double price
) {
}
