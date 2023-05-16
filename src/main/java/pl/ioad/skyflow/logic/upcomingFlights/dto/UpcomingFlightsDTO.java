package pl.ioad.skyflow.logic.upcomingFlights.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonFormat(pattern="yyyy-MM-dd HH:mm")
public class UpcomingFlightsDTO {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @Schema(description = "date of the departure", example = "2023-07-12")
    private Date departureDate;
    @Schema(description = "departure airport code", example = "BAR")
    private String departureAirport;
    @Schema(description = "departure airport code", example = "WAR")
    private String arrivalAirport;
    @Schema(description = "capacity of the airplane", example = "60")
    private Integer capacity;
}
