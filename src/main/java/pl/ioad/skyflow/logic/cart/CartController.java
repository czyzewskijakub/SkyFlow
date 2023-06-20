package pl.ioad.skyflow.logic.cart;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ioad.skyflow.database.model.Cart;
import pl.ioad.skyflow.logic.cart.dto.CartDTO;
import pl.ioad.skyflow.logic.cart.payload.request.CartRequest;
import pl.ioad.skyflow.logic.cart.payload.request.RemoveFromCartRequest;
import pl.ioad.skyflow.logic.cart.payload.response.AddToCartResponse;
import pl.ioad.skyflow.logic.cart.payload.response.CheckoutResponse;
import pl.ioad.skyflow.logic.cart.payload.response.RemoveFromCartResponse;
import pl.ioad.skyflow.logic.upcomingFlights.dto.UpcomingFlightsDTO;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @Operation(summary = "Get all items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved cart items", content = @Content(
                    schema = @Schema(example = """
                            [
                              {
                                "ticketId": 2
                              }
                            ]
                            """
                    ))
            ),
            @ApiResponse(responseCode = "400", description = "Not correct request", content = @Content(
                    schema = @Schema(example = """
                            {
                              "httpStatus": 400,
                              "exception": "Exception",
                              "message": "Bad request"
                            }
                            """
                    ))
            ),
            @ApiResponse(responseCode = "401", description = "You are not authorized to check items in cart", content = @Content(
                    schema = @Schema(example = """
                            {
                              "httpStatus": 401,
                              "exception": "Exception",
                              "message": "Unauthorized"
                            }
                            """
                    ))
            ),
            @ApiResponse(responseCode = "403", description = "You cannot perform the operation", content = @Content(
                    schema = @Schema(example = """
                            {
                              "httpStatus": 403,
                              "exception": "Exception",
                              "message": "Forbidden"
                            }
                            """
                    ))
            ),
            @ApiResponse(responseCode = "404", description = "Items not found", content = @Content(
                    schema = @Schema(example = """
                            {
                              "httpStatus": 404,
                              "exception": "Exception",
                              "message": "Not found"
                            }
                            """
                    ))
            )
    })
    @GetMapping("/get")
    public ResponseEntity<List<CartDTO>> getCartItems(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(cartService.getCartItems(httpServletRequest));
    }

    @Operation(summary = "Add item to cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added ticket to cart", content = @Content(
                    schema = @Schema(example = """
                            {
                              "message": "Successfully added ticket to cart"
                            }
                            """
                    ))
            ),
            @ApiResponse(responseCode = "400", description = "Not correct request", content = @Content(
                    schema = @Schema(example = """
                            {
                              "httpStatus": 400,
                              "exception": "Exception",
                              "message": "Bad request"
                            }
                            """
                    ))
            ),
            @ApiResponse(responseCode = "401", description = "You are not authorized to add ticket to cart", content = @Content(
                    schema = @Schema(example = """
                            {
                              "httpStatus": 401,
                              "exception": "Exception",
                              "message": "Unauthorized"
                            }
                            """
                    ))
            ),
            @ApiResponse(responseCode = "403", description = "You cannot perform the operation", content = @Content(
                    schema = @Schema(example = """
                            {
                              "httpStatus": 403,
                              "exception": "Exception",
                              "message": "Forbidden"
                            }
                            """
                    ))
            ),
            @ApiResponse(responseCode = "404", description = "Ticket not found", content = @Content(
                    schema = @Schema(example = """
                            {
                              "httpStatus": 404,
                              "exception": "Exception",
                              "message": "Not found"
                            }
                            """
                    ))
            ),
            @ApiResponse(responseCode = "406", description = "Ticket already in cart", content = @Content(
                    schema = @Schema(example = """
                                               {
                            "httpStatus": "NOT_ACCEPTABLE",
                             "exception": "DuplicatedDataException",
                             "message": "Ticket already in cart"
                                               }
                                               """
                    ))
            )
    })

    @PostMapping("/add")
    public ResponseEntity<AddToCartResponse> addToCart(CartRequest cartRequest,
                                                       HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(cartService.addToCart(cartRequest,httpServletRequest));
    }

    @Operation(summary = "Remove item from cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully removed item", content = @Content(
                    schema = @Schema(example = """
                            {
                              "message": "Successfully removed item"
                            }
                            """
                    ))
            ),
            @ApiResponse(responseCode = "400", description = "Not correct request", content = @Content(
                    schema = @Schema(example = """
                            {
                              "httpStatus": 400,
                              "exception": "Exception",
                              "message": "Bad request"
                            }
                            """
                    ))
            ),
            @ApiResponse(responseCode = "401", description = "You are not authorized to remove ticket from cart", content = @Content(
                    schema = @Schema(example = """
                            {
                              "httpStatus": 401,
                              "exception": "Exception",
                              "message": "Unauthorized"
                            }
                            """
                    ))
            ),
            @ApiResponse(responseCode = "403", description = "You cannot perform the operation", content = @Content(
                    schema = @Schema(example = """
                            {
                              "httpStatus": 403,
                              "exception": "Exception",
                              "message": "Forbidden"
                            }
                            """
                    ))
            ),
            @ApiResponse(responseCode = "404", description = "Item not found in cart", content = @Content(
                    schema = @Schema(example = """
                            {
                              "httpStatus": 404,
                              "exception": "Exception",
                              "message": "Not found"
                            }
                            """
                    ))
            )
    })
    @DeleteMapping("/remove")
    public ResponseEntity<RemoveFromCartResponse> removeFromCart(
            @RequestParam Long ticketId,
            HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(cartService.removeFromCart(ticketId, httpServletRequest));
    }
    @GetMapping("/checkout")
    public ResponseEntity<CheckoutResponse> calculateTotalPrice(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(cartService.calculateTotalPrice(httpServletRequest));
    }

}
