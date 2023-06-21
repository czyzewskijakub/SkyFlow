package pl.ioad.skyflow.logic.flight;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.ioad.skyflow.logic.flight.payload.FlightSearchRequest;
import pl.ioad.skyflow.logic.user.payload.request.LoginRequest;
import pl.ioad.skyflow.logic.user.payload.response.AuthorizationResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class FlightControllerTest {

    private static final LoginRequest CREDENTIALS = new LoginRequest(
            "example@gmail.com",
            "123abC#"
    );

    private static final FlightSearchRequest FLIGHT_SEARCH_REQUEST = new FlightSearchRequest(
            "EDDF",
            "2018-01-29 13:00",
            "2018-01-29 14:00"
    );

    private static final String UPCOMING_FLIGHTS_ENDPOINT = "/upcomingFlights/getAll";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mockMvc;
    private String token;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.token = login();
    }

    @Test
    public void test() throws Exception {
        var request = post(UPCOMING_FLIGHTS_ENDPOINT)
                .header("Authorization", this.token)
                .contentType(APPLICATION_JSON)
                .content(mockRequestBody(FLIGHT_SEARCH_REQUEST));
        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(44));
    }

    private String login() {
        var request = post("/users/login")
                .contentType(APPLICATION_JSON)
                .content(mockRequestBody(CREDENTIALS));
        try {
            var response = this.mockMvc.perform(request)
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
            return "Bearer " + mapper.readValue(response, AuthorizationResponse.class).getToken();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String mockRequestBody(final Object requestBody) {
        try {
            return mapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}