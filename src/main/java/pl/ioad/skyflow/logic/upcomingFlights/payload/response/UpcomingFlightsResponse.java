package pl.ioad.skyflow.logic.upcomingFlights.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public record UpcomingFlightsResponse (
    @Schema(description = "Reservation operation message response", example = "Succefully added flight")
    String message
) {}