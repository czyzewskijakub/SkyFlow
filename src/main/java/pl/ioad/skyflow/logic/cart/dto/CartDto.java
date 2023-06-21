package pl.ioad.skyflow.logic.cart.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.Builder;

@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
@Builder
public record CartDto(
        @Schema(description = "ticket_id", example = "1")
        Long ticketId,
        @Schema(description = "time, when ticket has been added to cart", example = "2023-07-12 12:50")
        Date additionDate) {

}