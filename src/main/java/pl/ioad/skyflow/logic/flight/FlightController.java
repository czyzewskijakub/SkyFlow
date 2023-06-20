package pl.ioad.skyflow.logic.flight;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ioad.skyflow.database.model.UpcomingFlight;
import pl.ioad.skyflow.logic.flight.payload.FlightSearchRequest;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @Operation(summary = "Find departures from given airport in specified time range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight states vectors found"),
            @ApiResponse(responseCode = "404", description = "Flight states vectors not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "401", description = "U are not authorized"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
    })
    @PostMapping(value = "/find", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UpcomingFlight>> findDepartures(@Parameter(description = "Flight search request", required = true)
                                                       @RequestBody FlightSearchRequest request) {

        return ok().body(flightService.findFlight(request));
    }

}
