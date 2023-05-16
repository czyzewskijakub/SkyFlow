package pl.ioad.skyflow.logic.reservation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class ReservationController {

    @Operation(summary = "Book a flight")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully booked flight"),
            @ApiResponse(responseCode = "404", description = "Flight not found"),
            @ApiResponse(responseCode = "403", description = "You cannot perform the operation"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to book a flight"),
            @ApiResponse(responseCode = "400", description = "Not correct request")
    })
    @PostMapping("/book")
    public ResponseEntity<?> bookFlight(
            @Parameter(description = "Flight booking request body", required = true)
            @Valid @RequestBody FlightRequest request) {
        return null;
    }


    @Operation(summary = "Cancel a flight reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully cancelled flight"),
            @ApiResponse(responseCode = "404", description = "Flight not found"),
            @ApiResponse(responseCode = "403", description = "You cannot perform the operation"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to cancel a flight"),
            @ApiResponse(responseCode = "400", description = "Not correct request")
    })
    @PostMapping("/cancel")
    public ResponseEntity<?> cancelReservation(
            @Parameter(description = "Flight cancellation request body", required = true)
            @Valid @RequestBody CancelRequest request) {
        return null;
    }
}
