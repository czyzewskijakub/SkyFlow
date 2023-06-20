package pl.ioad.skyflow.logic.cart.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CheckoutResponse(
        @Schema(description = "totalPrice of all elements in cart", example = "")
        Double totalPrice) {
}
