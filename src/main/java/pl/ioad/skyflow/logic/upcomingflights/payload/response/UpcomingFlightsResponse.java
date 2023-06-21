package pl.ioad.skyflow.logic.upcomingflights.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpcomingFlightsResponse(
    @Schema(description = "Flight operation message response", example = "Successfully added flight")
    String message
) {}