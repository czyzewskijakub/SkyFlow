package pl.ioad.skyflow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest
class RoutesTest {

    @Test
    void getAllFlights() {
        Routes routes = new Routes();
        WebTestClient client = WebTestClient.bindToRouterFunction(routes.getAllFlights())
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