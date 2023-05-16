package pl.ioad.skyflow.logic.ticket;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ioad.skyflow.logic.ticket.dto.TicketDTO;
import pl.ioad.skyflow.logic.ticket.payload.request.TicketsListRequest;
import pl.ioad.skyflow.logic.ticket.payload.request.TicketsRequest;
import pl.ioad.skyflow.logic.ticket.payload.response.TicketResponse;
import pl.ioad.skyflow.logic.upcomingFlights.UpcomingFlightsService;
import pl.ioad.skyflow.logic.upcomingFlights.dto.UpcomingFlightsDTO;
import pl.ioad.skyflow.logic.upcomingFlights.payload.request.UpcomingFlightRequest;
import pl.ioad.skyflow.logic.upcomingFlights.payload.response.UpcomingFlightsResponse;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @Operation(summary = "Add a ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added ticket"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "403", description = "You cannot perform the operation"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to add a ticket"),
            @ApiResponse(responseCode = "400", description = "Not correct request")
    })
    @PostMapping("/add")
    public ResponseEntity<TicketResponse> addFlight(
            @Parameter(description = "Ticket adding request body", required = true)
            @Valid @RequestBody TicketsRequest request,
            @Parameter(description = "HTTP Servlet Request", required = true)
            HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(ticketService.createTicket(request, httpServletRequest));
    }

    @Operation(summary = "Get all tickets")
    @GetMapping("/getAll")
    public ResponseEntity<List<TicketDTO>> getTickets() {
        return ResponseEntity.ok().body(ticketService.getTickets());
    }

    @Operation(summary = "Clear all flights")
    @GetMapping("/clear")
    public ResponseEntity<TicketResponse> clearTickets() {
        return ResponseEntity.ok().body(ticketService.clearTickets());
    }
}