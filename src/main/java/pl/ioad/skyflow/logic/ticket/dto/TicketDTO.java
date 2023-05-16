package pl.ioad.skyflow.logic.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;



@Builder
public record TicketDTO (
    @Schema(description = "ticket's price[USD]", example = "120")
    Double price,
    @Schema(description = "ticket's availability", example = "false")
    Boolean availability,
    @Schema(description = "ticket's seat", example = "A12")
    String seat,
    @Schema(description = "flight's ID", example = "1")
    Long id) {

}
