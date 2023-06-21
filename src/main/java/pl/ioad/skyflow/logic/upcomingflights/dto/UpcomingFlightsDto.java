package pl.ioad.skyflow.logic.upcomingflights.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.Builder;

@Builder
@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
public record UpcomingFlightsDto(
    @Schema(description = "id of vehicle", example = "3c4ad0")
    String icao24,
    @Schema(description = "date of the departure", example = "2023-07-12 12:50")
    Date departureDate,
    @Schema(description = "departure airport code", example = "BAR")
    String departureAirport,
    @Schema(description = "departure airport code", example = "WAR")
    String arrivalAirport,
    @Schema(description = "capacity of the airplane", example = "60")
    Integer capacity
    ) {
}
