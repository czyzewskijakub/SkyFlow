package pl.ioad.skyflow.logic.ticket;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ioad.skyflow.database.model.TicketStatus;
import pl.ioad.skyflow.database.model.TravelClass;
import pl.ioad.skyflow.logic.ticket.dto.TicketDto;
import pl.ioad.skyflow.logic.ticket.payload.request.FlightRequest;
import pl.ioad.skyflow.logic.ticket.payload.response.TicketResponse;

@RestController
@RequestMapping("/tickets")
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
    @PutMapping("/cancel")
    public ResponseEntity<TicketResponse> cancel(
            @Parameter(description = "Flight cancellation ticket id", required = true)
            @Valid @RequestParam Long ticketId,
            @Parameter(description = "HTTP Servlet Request", required = true)
            HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(ticketService.cancelTicket(ticketId, httpServletRequest));
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
    @GetMapping
    public ResponseEntity<List<TicketDto>> getTickets(@Parameter(description = "HTTP Servlet Request", required = true)
                                                                   HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(ticketService.retrieveTickets(httpServletRequest));
    }

    @Operation(summary = "Get all possible travel classes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved travel classes", content = @Content(
                    schema = @Schema(example = """
                                                [
                                                  "ECONOMY",
                                                  "PREMIUM",
                                                  "BUSINESS",
                                                  "FIRST"
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
            )
    })
    @GetMapping("/classes")
    public ResponseEntity<List<TravelClass>> getClasses() {
        return ResponseEntity.ok().body(ticketService.getClasses());
    }

    @Operation(summary = "Change status for the given ticket as an admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully changed ticket status", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                    "message": "Successfully changed ticket status"
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
            )
    })
    @PutMapping("/status/{ticketId}")
    public ResponseEntity<TicketResponse> changeTicketStatus(@PathVariable Long ticketId, @RequestParam TicketStatus status) {
        return ResponseEntity.ok().body(ticketService.changeTicketStatus(ticketId, status));
    }


    @Operation(summary = "Retrieve all users tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all users tickets", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                    "message": "Successfully retrieved all users tickets"
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
            @ApiResponse(responseCode = "403", description = "You cannot perform the operation", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 403,
                                                  "exception": "Exception",
                                                  "message": "Forbidden"
                                                }
                                                """
                    ))
            )
    })
    @GetMapping("/users")
    public ResponseEntity<List<TicketDto>> getAllUsersTickets() {
        return ResponseEntity.ok().body(ticketService.retrieveAllUsersTickets());
    }

}
