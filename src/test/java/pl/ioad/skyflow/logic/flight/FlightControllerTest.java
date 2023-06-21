package pl.ioad.skyflow.logic.flight;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import pl.ioad.skyflow.TestUtils;
import pl.ioad.skyflow.logic.flight.payload.FlightSearchRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
class FlightControllerTest {

    private static final FlightSearchRequest FLIGHT_SEARCH_REQUEST = new FlightSearchRequest(
            "EDDF",
            "2023-06-24 04:00",
            "2023-06-24 06:00"
    );

    private static final String UPCOMING_FLIGHTS_ENDPOINT = "/upcomingFlights/getAll";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mockMvc;
    private String token;
    private TestUtils utils;

    @BeforeEach
    public void setup() {
        this.mockMvc = webAppContextSetup(this.webApplicationContext).build();
        this.utils = new TestUtils(this.mockMvc, this.mapper);
        this.token = this.utils.login();
    }

    @Test
    public void shouldReturnFlightsInGivenTimeRange() throws Exception {
        var request = post(UPCOMING_FLIGHTS_ENDPOINT)
                .header("Authorization", this.token)
                .contentType(APPLICATION_JSON)
                .content(this.utils.mockRequestBody(FLIGHT_SEARCH_REQUEST));
        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(13));
    }
}