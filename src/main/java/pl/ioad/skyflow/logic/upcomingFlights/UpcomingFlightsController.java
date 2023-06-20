package pl.ioad.skyflow.logic.upcomingFlights;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ioad.skyflow.logic.upcomingFlights.dto.UpcomingFlightsDTO;
import pl.ioad.skyflow.logic.upcomingFlights.payload.request.UpcomingFlightRequest;
import pl.ioad.skyflow.logic.upcomingFlights.payload.response.UpcomingFlightsResponse;

import java.util.List;

@RestController
@RequestMapping("/upcomingFlights")
@RequiredArgsConstructor
public class UpcomingFlightsController {
    private final UpcomingFlightsService upcomingFlightsService;

    @Operation(summary = "Add a flight")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added flight"),
            @ApiResponse(responseCode = "404", description = "Flight not found"),
            @ApiResponse(responseCode = "403", description = "You cannot perform the operation"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to add a flight"),
            @ApiResponse(responseCode = "400", description = "Not correct request")
    })
    @PostMapping("/add")
    public ResponseEntity<UpcomingFlightsResponse> addFlight(
            @Parameter(description = "Flight adding request body", required = true)
            @Valid @RequestBody UpcomingFlightRequest request,
            @Parameter(description = "HTTP Servlet Request", required = true)
            HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(upcomingFlightsService.addFlight(request,httpServletRequest));
    }
    @Operation(summary = "Get all flights")
    @GetMapping("/getAll")
    public ResponseEntity<List<UpcomingFlightsDTO>> getFlights() {
        return ResponseEntity.ok().body(upcomingFlightsService.getFlights());
    }
    @Operation(summary = "Clear all flights")
    @GetMapping("/clear")
    public ResponseEntity<UpcomingFlightsResponse> clearFlights() {
        return ResponseEntity.ok().body(upcomingFlightsService.clearFlights());
    }
}
