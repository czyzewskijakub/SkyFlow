package pl.ioad.skyflow;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest
@Import({RoutesConfiguration.class, FlightsHandler.class})
class RoutesTest {

    @Autowired
    RoutesConfiguration routes;

    @Autowired
    FlightsHandler handler;

    @Test
    @Disabled
    void getAllFlights() {
        WebTestClient client = WebTestClient.bindToRouterFunction(routes.getAllFlights(handler))
                .build();

        String expectedMessage = "All flights here";

        client.get()
                .uri("/all")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo(expectedMessage);
    }
}