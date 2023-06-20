package pl.ioad.skyflow.logic.ticket;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ioad.skyflow.logic.ticket.dto.TicketDTO;
import pl.ioad.skyflow.logic.ticket.payload.request.CancelRequest;
import pl.ioad.skyflow.logic.ticket.payload.request.FlightRequest;
import pl.ioad.skyflow.logic.ticket.payload.response.TicketResponse;

import java.util.List;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @Operation(summary = "Book a flight")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully booked flight", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "message": "Successfully booked flight"
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
            @ApiResponse(responseCode = "401", description = "You are not authorized to book a flight", content = @Content(
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
            @ApiResponse(responseCode = "404", description = "Flight not found", content = @Content(
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
    @PostMapping("/book")
    public ResponseEntity<TicketResponse> book(
            @Parameter(description = "Flight booking request body", required = true)
            @Valid @RequestBody FlightRequest request,
            @Parameter(description = "HTTP Servlet Request", required = true)
            HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(ticketService.bookFlight(request, httpServletRequest));
    }


    @Operation(summary = "Cancel a flight ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully cancelled flight", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "message": "Successfully canceled flight"
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
            @ApiResponse(responseCode = "401", description = "You are not authorized to cancel a flight", content = @Content(
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
            @ApiResponse(responseCode = "404", description = "Flight not found", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 404,
                                                  "exception": "Exception",
                                                  "message": "Not found"
                                                }
                                                """
                    ))
            ),
    })
    @PostMapping("/cancel")
    public ResponseEntity<TicketResponse> cancel(
            @Parameter(description = "Flight cancellation request body", required = true)
            @Valid @RequestBody CancelRequest request,
            @Parameter(description = "HTTP Servlet Request", required = true)
            HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(ticketService.cancelFlight(request, httpServletRequest));
    }

    @Operation(summary = "Get your tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user flights", content = @Content(
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
            @ApiResponse(responseCode = "401", description = "You are not authorized to cancel a flight", content = @Content(
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
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(
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
    @GetMapping("/tickets")
    public ResponseEntity<List<TicketDTO>> getTickets(@Parameter(description = "HTTP Servlet Request", required = true)
                                                                   HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(ticketService.retrieveTickets(httpServletRequest));
    }

}
