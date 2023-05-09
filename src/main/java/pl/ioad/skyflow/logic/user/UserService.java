package pl.ioad.skyflow.logic.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.ioad.skyflow.database.model.User;
import pl.ioad.skyflow.database.repository.UserRepository;
import pl.ioad.skyflow.logic.user.security.jwt.JwtUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;

    public User register(String firstName, String lastName, String email, String password, String profilePictureUrl) {
        if (validateRegisterInput(email, password)) {
            System.out.println("Wrong register input or email is taken");
        }
        return userRepository.save(User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .passwordHash(encoder.encode(password))
                .profilePictureUrl(profilePictureUrl)
                .isAdmin(false)
                .build());

    }

    public User registerAdmin(String firstName, String lastName, String email, String password, String profilePictureUrl) {
        if (validateRegisterInput(email, password)) {
            System.out.println("Wrong register input or email is taken");
        }
        return userRepository.save(User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .passwordHash(encoder.encode(password))
                .profilePictureUrl(profilePictureUrl)
                .isAdmin(true)
                .build());
    }

    public String login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateJwtToken(authentication);
    }

    public User getUserByToken(String token) {
        var user = userRepository.findByEmail(jwtUtils.extractUsername(token));
        return user.orElse(null);
    }

    private boolean validateRegisterInput(String email, String password) {
        if (!email.contains("@") && !email.contains(".") || password == null) {
            return true;
        } else return userRepository.findByEmail(email).isPresent();
    }
}
