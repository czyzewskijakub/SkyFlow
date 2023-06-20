package pl.ioad.skyflow.logic.cart.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;

public record CartRequest(
        @NonNull
        @Schema(description = "id of ticket to add/remove from cart", example = "1")
        Long ticketId) {
}
