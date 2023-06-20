package pl.ioad.skyflow.logic.cart.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record RemoveFromCartResponse(
        @Schema(description = "Removing from cart operation message response", example = "Successfully removed from cart")
        String message
) {
}
