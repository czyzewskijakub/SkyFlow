package pl.ioad.skyflow.logic.upcomingflights;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ioad.skyflow.logic.flight.payload.FlightSearchRequest;
import pl.ioad.skyflow.logic.upcomingflights.dto.UpcomingFlightsDto;
import pl.ioad.skyflow.logic.upcomingflights.payload.request.UpcomingFlightRequest;
import pl.ioad.skyflow.logic.upcomingflights.payload.response.UpcomingFlightsResponse;

@RestController
@RequestMapping("/upcomingFlights")
@RequiredArgsConstructor
public class UpcomingFlightsController {
    private final UpcomingFlightsService upcomingFlightsService;

    @Operation(summary = "Add an upcoming flight")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added flight", content = @Content(
                    schema = @Schema(example = """
                            {
                              "message": "Successfully added flight"
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
            @ApiResponse(responseCode = "401", description = "You are not authorized to add a flight", content = @Content(
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
    @PostMapping("/add")
    public ResponseEntity<UpcomingFlightsResponse> addFlight(
            @Parameter(description = "Flight adding request body", required = true)
            @Valid @RequestBody UpcomingFlightRequest request) {
        return ResponseEntity.ok().body(upcomingFlightsService.addFlight(request));
    }

    @Operation(summary = "Get all flights")
    @PostMapping(value = "/getAll", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UpcomingFlightsDto>> getFlights(@Parameter(description = "Flight search request", required = true)
                                                               @RequestBody FlightSearchRequest request) {
        return ResponseEntity.ok().body(upcomingFlightsService.getFlights(request));
    }

    @Operation(summary = "Clear all flights")
    @GetMapping("/clear")
    public ResponseEntity<UpcomingFlightsResponse> clearFlights() {
        return ResponseEntity.ok().body(upcomingFlightsService.clearFlights());
    }
}
