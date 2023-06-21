package pl.ioad.skyflow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.test.web.servlet.MockMvc;
import pl.ioad.skyflow.logic.user.payload.request.LoginRequest;
import pl.ioad.skyflow.logic.user.payload.response.AuthorizationResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RequiredArgsConstructor
public class TestUtils {

    private static final LoginRequest CREDENTIALS = new LoginRequest(
            "example@gmail.com",
            "123abC#"
    );

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    public String login() {
        var request = post("/users/login")
                .contentType(APPLICATION_JSON)
                .content(mockRequestBody(CREDENTIALS));
        try {
            var response = mockMvc.perform(request)
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
            return "Bearer " + mapper.readValue(response, AuthorizationResponse.class).getToken();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String mockRequestBody(final Object requestBody) {
        try {
            return mapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
