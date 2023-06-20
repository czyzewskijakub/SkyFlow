package pl.ioad.skyflow.logic.upcomingFlights.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

public record UpcomingFlightRequest (

    @NonNull
    @Schema(description = "id of vehicle", example = "3c4ad0")
    String icao24,

    @NonNull
    @Schema(description = "Date of departure", pattern = "yyyy-MM-dd HH:mm" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    Date departureDate,

    @NonNull
    @Schema(description = "Name of departure airport", example = "BAR")
    String departureAirport,

    @NonNull
    @Schema(description = "Name of arrival airport", example = "WAR")
    String arrivalAirport,

    @NonNull
    @Schema(description = "Airplane's capacity", example = "60")
    Integer capacity
) {}
