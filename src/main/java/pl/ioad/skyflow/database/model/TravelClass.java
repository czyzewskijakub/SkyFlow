package pl.ioad.skyflow.database.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TravelClass {
    ECONOMY(1.0),
    PREMIUM(1.5),
    BUSINESS(2.0),
    FIRST(3.0);

    private final double weight;

}
