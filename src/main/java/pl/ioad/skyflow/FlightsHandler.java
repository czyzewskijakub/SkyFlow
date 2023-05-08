package pl.ioad.skyflow;

import org.opensky.api.OpenSkyApi;
import org.opensky.model.OpenSkyStates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Component
public class FlightsHandler {

    @Value("${opensky.username}")
    private String USERNAME;
    @Value("${opensky.password}")
    private String PASSWORD;

    public Mono<ServerResponse> getAllFlights(ServerRequest request) {
        OpenSkyApi api = new OpenSkyApi(USERNAME, PASSWORD);
        try {
            // Current flights on Switzerland area
            var bbox = new OpenSkyApi.BoundingBox(45.8389, 47.8229, 5.9962, 10.5226);
            var states = api.getStates(0, null, bbox).getStates();
            return ok()
                    .contentType(APPLICATION_JSON)
                    .body(Mono.just(states), OpenSkyStates.class);
        } catch (IOException e) {
            return status(NOT_FOUND).build();
        }
    }

}
