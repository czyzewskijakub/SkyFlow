package pl.ioad.skyflow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest
@ContextConfiguration(classes = Routes.class)
class RoutesTest {

    @Autowired
    Routes routes;

    @Test
    void getAllFlights() {
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