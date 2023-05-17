package pl.ioad.skyflow.logic.reservation.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;


@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
public class FlightRequest {

    @NonNull
    @Schema(description = "Date of departure", example = "1980-04-09")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date departureDate;

    @NonNull
    @Schema(description = "Date of arrival", example = "1980-04-10")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date arrivalDate;

    @NotBlank
    @NonNull
    @Schema(description = "Name of departure airport", example = "EDDF")
    private String departureAirport;

    @NotBlank
    @NonNull
    @Schema(description = "Name of arrival airport", example = "EDDF")
    private String arrivalAirport;

    @NotBlank
    @NonNull
    @Schema(description = "Name of airline", example = "American Airlines")
    private String airline;

    @NotBlank
    @NonNull
    @Schema(description = "A travel class is a quality of accommodation on public transport", example = "Economy")
    private String travelClass;

    @NotBlank
    @NonNull
    @Schema(description = "Number of reserved seat", example = "A12")
    private String seatNumber;
}
