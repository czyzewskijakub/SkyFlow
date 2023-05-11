package pl.ioad.skyflow.logic.flight.opensky;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "opensky")
public record Credentials(
        String username,
        String password
) { }
