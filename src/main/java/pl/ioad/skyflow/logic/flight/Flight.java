package pl.ioad.skyflow.logic.flight;

import pl.ioad.skyflow.logic.flight.opensky.OpenSkyFlight;

public record Flight(OpenSkyFlight openSkyFlight, int capacity) {
}
