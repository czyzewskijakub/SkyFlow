package pl.ioad.skyflow.logic.ticket.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;


@JsonFormat(pattern="yyyy-MM-dd HH:mm")
public record FlightRequest (
    @NonNull
    @Schema(description = "Date of departure", example = "1980-04-09")
    Date departureDate,

    @NonNull
    @Schema(description = "Date of arrival", example = "1980-04-10")
    Date arrivalDate,

    @NotBlank
    @NonNull
    @Schema(description = "Name of departure airport", example = "EDDF")
    String departureAirport,

    @NotBlank
    @NonNull
    @Schema(description = "Name of arrival airport", example = "EDDF")
    String arrivalAirport,

    @NotBlank
    @NonNull
    @Schema(description = "Name of airline", example = "American Airlines")
    String airline,

    @NotBlank
    @NonNull
    @Schema(description = "A travel class is a quality of accommodation on public transport", example = "Economy")
    String travelClass,

    @NotBlank
    @NonNull
    @Schema(description = "Number of reserved seat", example = "A12")
    String seatNumber
) {}
