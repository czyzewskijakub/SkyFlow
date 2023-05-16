package pl.ioad.skyflow.logic.upcomingFlights.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@JsonFormat(pattern="yyyy-MM-dd HH:mm")
public class UpcomingFlightRequest {
    @NonNull
    @Schema(description = "Date of departure", pattern = "yyyy-MM-dd HH:mm" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date departureDate;

    @NonNull
    @Schema(description = "Name of departure airport", example = "BAR")
    private String departureAirport;

    @NonNull
    @Schema(description = "Name of arrival airport", example = "WAR")
    private String arrivalAirport;

    @NonNull
    @Schema(description = "Airplane's capacity", example = "60")
    private Integer capacity;
}
