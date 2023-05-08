package pl.ioad.skyflow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoutesConfiguration {

    @Bean
    public RouterFunction<ServerResponse> getAllFlights() {
        return route(GET("/all"), FlightsHandler::getAllFlights);
    }

}
