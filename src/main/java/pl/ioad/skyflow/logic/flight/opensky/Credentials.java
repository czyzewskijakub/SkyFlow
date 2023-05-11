package pl.ioad.skyflow.logic.flight.opensky;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Base64;

@ConfigurationProperties(prefix = "opensky")
public record Credentials(
        String username,
        String password
) {
    public String getBasicAuthenticationHeader() {
        String authString = username + ":" + password;
        String authEncoded = Base64.getEncoder().encodeToString(authString.getBytes());
        return "Basic " + authEncoded;
    }
}
