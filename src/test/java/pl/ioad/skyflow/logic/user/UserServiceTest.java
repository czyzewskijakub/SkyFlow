package pl.ioad.skyflow.logic.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;
import pl.ioad.skyflow.TestUtils;
import pl.ioad.skyflow.database.repository.UserRepository;
import pl.ioad.skyflow.logic.user.payload.request.UserDataRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.allOf;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper mapper;

    private static final String REGISTER_USER_ENDPOINT = "/users/register";

    private MockMvc mockMvc;
    private String token;
    private TestUtils utils;

    // Test data
    private final String firstName = "John";
    private final String lastName = "Smith";
    private final String email = "johnsmith@gmail.com";
    private final String pictureUrl = "https://pl.pinterest.com/pin/327848047887112192/";
    private final String password = "password";

    @BeforeEach
    public void setup() {
        this.mockMvc = webAppContextSetup(this.webApplicationContext).build();
        this.utils = new TestUtils(this.mockMvc, this.mapper);
        this.token = this.utils.login();
    }

    @Test
    void testRegisterUserAndDelete_Success() throws Exception {

        UserDataRequest userData = UserDataRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .pictureUrl(pictureUrl)
                .build();

        var request = post(REGISTER_USER_ENDPOINT)
                .header("Authorization", this.token)
                .contentType(APPLICATION_JSON)
                .content(this.utils.mockRequestBody(userData));
        this.mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON));

        assertThat(userRepository.findByEmail(email))
                .isPresent()
                .get()
                .has(allOf(
                        new Condition<>(u -> u.getFirstName().equals(firstName), "first name"),
                        new Condition<>(u -> u.getLastName().equals(lastName), "last name"),
                        new Condition<>(u -> u.getProfilePictureUrl().equals(pictureUrl), "picture url"),
                        new Condition<>(u -> !u.getPasswordHash().equals(password), "password hash")
                ));

        userService.deleteUserByEmail(email);
    }

    @Test
    void testRegisterUser_Failure() throws Exception {

        UserDataRequest userData = UserDataRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .pictureUrl(pictureUrl)
                .build();

        var request = post(REGISTER_USER_ENDPOINT)
                .header("Authorization", this.token)
                .contentType(APPLICATION_JSON)
                .content(this.utils.mockRequestBody(userData));
        this.mockMvc.perform(request)
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(MethodArgumentNotValidException.class));

        assertThat(userRepository.existsByEmail(email)).isFalse();
    }

}