package pl.ioad.skyflow.logic.flight.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import static pl.ioad.skyflow.logic.utils.TimeUtils.subtractDateTime;
import static pl.ioad.skyflow.logic.utils.TimeUtils.toUnixTime;

public record FlightSearchRequest(
        @Schema(description = "ICAO code of airport", example = "EDDF")
        @NotBlank
        String departureAirport,
        @Schema(description = "begin date", example = "2023-06-17 00:00")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
        @NotBlank
        String begin,
        @Schema(description = "begin date", example = "2023-06-17 23:59")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
        @NotBlank
        String end) {

    public FlightSearchRequest withSubtractedTime() {
        var begin = subtractDateTime(this.begin);
        var end = subtractDateTime(this.end);
        return new FlightSearchRequest(this.departureAirport, begin, end);
    }

    public FlightSearchRequest withUnixTime() {
        var begin = toUnixTime(this.begin);
        var end = toUnixTime(this.end);
        return new FlightSearchRequest(this.departureAirport, begin, end);
    }
}
