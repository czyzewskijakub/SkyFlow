package pl.ioad.skyflow.logic.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import pl.ioad.skyflow.database.model.TicketStatus;
import pl.ioad.skyflow.database.model.TravelClass;


@Builder
public record TicketDto(
    @Schema(description = "ID number of flight ticket", example = "1")
    Long ticketId,
    @Schema(description = "price of ticket in EUR", example = "100")
    Double price,
    @Schema(description = "Travel class", example = "ECONOMY")
    TravelClass travelClass,
    @Schema(description = "Ticket status", example = "VALID")
    TicketStatus ticketStatus
) {
}
