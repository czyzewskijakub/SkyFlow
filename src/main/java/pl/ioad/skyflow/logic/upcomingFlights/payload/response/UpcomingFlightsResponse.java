package pl.ioad.skyflow.logic.upcomingFlights.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpcomingFlightsResponse {
    @Schema(description = "Reservation operation message response", example = "Succefully added flight")
    private String message;
}