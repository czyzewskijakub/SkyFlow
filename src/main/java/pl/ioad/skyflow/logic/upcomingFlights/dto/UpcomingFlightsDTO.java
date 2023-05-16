package pl.ioad.skyflow.logic.upcomingFlights.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Builder
@JsonFormat(pattern="yyyy-MM-dd HH:mm")
public record UpcomingFlightsDTO (
    @Schema(description = "date of the departure", example = "2023-07-12")
    Date departureDate,
    @Schema(description = "departure airport code", example = "BAR")
    String departureAirport,
    @Schema(description = "departure airport code", example = "WAR")
    String arrivalAirport,
    @Schema(description = "capacity of the airplane", example = "60")
    Integer capacity
    ) {
}