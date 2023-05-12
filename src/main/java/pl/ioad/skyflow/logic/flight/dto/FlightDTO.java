package pl.ioad.skyflow.logic.flight.dto;

public record FlightDTO(
        String icao24,
        Integer firstSeen,
        String estDepartureAirport,
        Integer lastSeen,
        String estArrivalAirport,
        String callsign,
        Integer estDepartureAirportHorizDistance,
        Integer estDepartureAirportVertDistance,
        Integer estArrivalAirportHorizDistance,
        Integer estArrivalAirportVertDistance,
        Integer departureAirportCandidatesCount,
        Integer arrivalAirportCandidatesCount
) {
}
