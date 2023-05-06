package pl.ioad.skyflow;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public abstract class FlightsHandler {

    public static Mono<ServerResponse> getAllFlights(ServerRequest request) {
        return ok().body(Mono.just("All flights here"), String.class);
    }

}
