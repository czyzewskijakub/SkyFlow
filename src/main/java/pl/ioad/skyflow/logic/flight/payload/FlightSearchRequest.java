package pl.ioad.skyflow.logic.flight.payload;

public record FlightSearchRequest(
        String departureAirport,
        Integer begin,
        Integer end
) { }
