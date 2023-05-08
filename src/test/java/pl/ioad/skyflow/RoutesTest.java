package pl.ioad.skyflow;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest
class RoutesTest {

    @Test
    @Disabled
    void getAllFlights() {
        RoutesConfiguration routes = new RoutesConfiguration();
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