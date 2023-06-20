package pl.ioad.skyflow.logic.cart.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record AddToCartResponse(
        @Schema(description = "Adding to cart operation message response", example = "Successfully added to cart")
        String message
) {
}
