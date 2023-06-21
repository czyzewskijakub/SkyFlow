package pl.ioad.skyflow.logic.flight.opensky;

import java.util.Base64;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
