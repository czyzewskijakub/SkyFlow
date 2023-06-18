package pl.ioad.skyflow.logic.user;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.ioad.skyflow.database.model.User;
import pl.ioad.skyflow.database.repository.UserRepository;
import pl.ioad.skyflow.logic.user.payload.request.UserDataRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testRegisterUser_Success() {
        String firstName = "John";
        String lastName = "Smith";
        String email = "johnsmith@gmail.com";
        String pictureUrl = "https://pl.pinterest.com/pin/327848047887112192/";
        String password = "password";

        User user = new User(1L, firstName, lastName, email, pictureUrl, password, false);

        UserDataRequest userData = new UserDataRequest(firstName, lastName, email, pictureUrl, password);

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        var registeredUser = userService.register(userData).getUser();

        verify(userRepository, times(1)).existsByEmail(anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(anyString());

        assertNotNull(registeredUser);
        assertEquals(email, registeredUser.getEmail());
    }

    @Test
    void testRegisterUser_Failure() {
        String firstName = "John";
        String lastName = "Smith";
        String pictureUrl = "https://pl.pinterest.com/pin/327848047887112192/";
        String password = "password";

        UserDataRequest userData = UserDataRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .pictureUrl(pictureUrl)
                .build();

        assertThrows(NullPointerException.class, () -> userService.register(userData));

        verify(userRepository, never()).existsByEmail(anyString());
    }

}