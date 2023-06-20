package pl.ioad.skyflow.logic.upcomingFlights.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpcomingFlightsResponse (
    @Schema(description = "Flight operation message response", example = "Succefully added flight")
    String message
) {}