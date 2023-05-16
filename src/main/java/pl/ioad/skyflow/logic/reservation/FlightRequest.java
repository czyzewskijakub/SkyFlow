package pl.ioad.skyflow.logic.reservation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
public class FlightRequest {
    @NotBlank
    @NonNull
    @Schema(description = "User first name", example = "John")
    private Date departureDate;

    @NotBlank
    @NonNull
    @Schema(description = "User first name", example = "John")
    private Date arrivalDate;

    @NotBlank
    @NonNull
    @Schema(description = "User first name", example = "John")
    private String departureAirport;

    @NotBlank
    @NonNull
    @Schema(description = "User first name", example = "John")
    private String arrivalAirport;

    @NotBlank
    @NonNull
    @Schema(description = "User first name", example = "John")
    private String airline;

    @NotBlank
    @NonNull
    @Schema(description = "User first name", example = "John")
    private String travelClass;

    @NotBlank
    @NonNull
    @Schema(description = "User first name", example = "John")
    private String seatNumber;
}
