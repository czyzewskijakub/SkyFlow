package pl.ioad.skyflow.logic.flight.opensky;

import io.swagger.v3.oas.annotations.media.Schema;

import static pl.ioad.skyflow.logic.utils.TimeUtils.addDateTime;
import static pl.ioad.skyflow.logic.utils.TimeUtils.toDateTime;

public record OpenSkyFlight(
        @Schema(description = "Unique ICAO 24-bit address of the transponder in hex string representation. All letters are lower case.", example = "3c4ad0")
        String icao24,
        @Schema(description = "Estimated time of departure for the flight as Unix time (seconds since epoch).", example = "1517230790")
        String firstSeen,
        @Schema(description = "ICAO code of the estimated departure airport. Can be null if the airport could not be identified.", example = "EDDF")
        String estDepartureAirport,
        @Schema(description = "Estimated time of arrival for the flight as Unix time (seconds since epoch)", example = "1517238306")
        String lastSeen,
        @Schema(description = "ICAO code of the estimated arrival airport. Can be null if the airport could not be identified.", example = "LSGG")
        String estArrivalAirport,
        @Schema(description = "Callsign of the vehicle (8 chars). Can be null if no callsign has been received. If the vehicle transmits multiple callsigns during the flight, we take the one seen most frequently", example = "DLH630  ")
        String callsign,
        @Schema(description = "Horizontal distance of the last received airborne position to the estimated departure airport in meters", example = "4319")
        Integer estDepartureAirportHorizDistance,
        @Schema(description = "Vertical distance of the last received airborne position to the estimated departure airport in meters", example = "65")
        Integer estDepartureAirportVertDistance,
        @Schema(description = "Horizontal distance of the last received airborne position to the estimated arrival airport in meters", example = "1803")
        Integer estArrivalAirportHorizDistance,
        @Schema(description = "Vertical distance of the last received airborne position to the estimated arrival airport in meters", example = "163")
        Integer estArrivalAirportVertDistance,
        @Schema(description = "Number of other possible departure airports. These are airports in short distance to estDepartureAirport.", example = "1")
        Integer departureAirportCandidatesCount,
        @Schema(description = "Number of other possible departure airports. These are airports in short distance to estArrivalAirport.", example = "0")
        Integer arrivalAirportCandidatesCount
) {
    public OpenSkyFlight(String icao24,
                         String firstSeen,
                         String estDepartureAirport,
                         String lastSeen,
                         String estArrivalAirport,
                         String callsign,
                         Integer estDepartureAirportHorizDistance,
                         Integer estDepartureAirportVertDistance,
                         Integer estArrivalAirportHorizDistance,
                         Integer estArrivalAirportVertDistance,
                         Integer departureAirportCandidatesCount,
                         Integer arrivalAirportCandidatesCount) {

        this.icao24 = icao24;
        this.firstSeen = addDateTime(toDateTime(firstSeen));
        this.estDepartureAirport = estDepartureAirport;
        this.lastSeen = addDateTime(toDateTime(lastSeen));
        this.estArrivalAirport = estArrivalAirport;
        this.callsign = callsign;
        this.estDepartureAirportHorizDistance = estDepartureAirportHorizDistance;
        this.estDepartureAirportVertDistance = estDepartureAirportVertDistance;
        this.estArrivalAirportHorizDistance = estArrivalAirportHorizDistance;
        this.estArrivalAirportVertDistance = estArrivalAirportVertDistance;
        this.departureAirportCandidatesCount = departureAirportCandidatesCount;
        this.arrivalAirportCandidatesCount = arrivalAirportCandidatesCount;
    }
}
