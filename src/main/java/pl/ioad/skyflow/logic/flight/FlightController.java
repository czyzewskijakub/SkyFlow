package pl.ioad.skyflow.logic.flight;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ioad.skyflow.logic.flight.dto.FlightDTO;

import java.util.List;

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
    @GetMapping("/find")
    public ResponseEntity<List<FlightDTO>> findDepartures(@Parameter(description = "ICAO identier for the airport", example = "EDDF", required = true)
                                                          @RequestParam String departureAirport,
                                                          @Parameter(description = "Start of time interval to retrieve flights for as Unix time", example = "1517227200", required = true)
                                                          @RequestParam Integer begin,
                                                          @Parameter(description = "End of time interval to retrieve flights for as Unix time", example = "1517230800", required = true)
                                                          @RequestParam Integer end) {

        return ok().body(flightService.findFlight(departureAirport, begin, end));
    }

}
