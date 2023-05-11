package pl.ioad.skyflow.logic.flight.opensky;

import lombok.Getter;

@Getter
public enum Endpoint {

    DEPARTURE("/flights/departure");

    Endpoint(String url) {
        this.url = url;
    }

    private final String url;

}
