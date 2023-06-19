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
        @Schema(description = "begin date", example = "2023-06-17 00:00:00")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
        @NotBlank
        String begin,
        @Schema(description = "begin date", example = "2023-06-17 23:59:59")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
        @NotBlank
        String end) {

    public FlightSearchRequest(String departureAirport, String begin, String end) {
        begin = subtractDateTime(begin);
        begin = toUnixTime(begin);
        end = subtractDateTime(end);
        end = toUnixTime(end);

        this.departureAirport = departureAirport;
        this.begin = begin;
        this.end = end;
    }
}
