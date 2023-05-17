package pl.ioad.skyflow.logic.user;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.ioad.skyflow.database.model.User;
import pl.ioad.skyflow.database.repository.UserRepository;
import pl.ioad.skyflow.logic.exception.type.AuthException;
import pl.ioad.skyflow.logic.exception.type.ForbiddenException;
import pl.ioad.skyflow.logic.exception.type.InvalidBusinessArgumentException;
import pl.ioad.skyflow.logic.exception.type.InvalidDataException;
import pl.ioad.skyflow.logic.user.dto.Mapper;
import pl.ioad.skyflow.logic.user.dto.UserDto;
import pl.ioad.skyflow.logic.user.payload.request.LoginRequest;
import pl.ioad.skyflow.logic.user.payload.request.RegisterRequest;
import pl.ioad.skyflow.logic.user.payload.response.AuthorizationResponse;
import pl.ioad.skyflow.logic.user.security.jwt.JwtUtils;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;
    private final Mapper mapper = new Mapper();

    public UserDto register(RegisterRequest request) {
        if (!request.getEmail().contains("@") && !request.getEmail().contains(".")) {
            throw new InvalidDataException("Wrong register input");
        } else if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ForbiddenException("Email is taken");
        }

        User user = userRepository.save(User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .passwordHash(encoder.encode(request.getPassword()))
                .profilePictureUrl(request.getPictureUrl())
                .isAdmin(false)
                .build());
        return mapper.mapUser(user);

    }

    public UserDto registerAdmin(RegisterRequest request, HttpServletRequest http) {
        String token = http.getHeader("Authorization");
        if (token == null)
            throw new ForbiddenException("You are not authorized");
        token = token.substring("Bearer ".length());
        var user = userRepository.findByEmail(jwtUtils.extractUsername(token));
        if (user.isPresent() && !user.get().getIsAdmin())
            throw new InvalidBusinessArgumentException("You cannot register new admin as standard user");
        else if (!request.getEmail().contains("@") && !request.getEmail().contains("."))
            throw new InvalidDataException("Wrong register input");
        else if (userRepository.findByEmail(request.getEmail()).isPresent())
            throw new ForbiddenException("Email is taken");

        User newUser = userRepository.save(User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .passwordHash(encoder.encode(request.getPassword()))
                .profilePictureUrl(request.getPictureUrl())
                .isAdmin(true)
                .build());
        return mapper.mapUser(newUser);
    }

    public AuthorizationResponse login(LoginRequest request, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getHeader(AUTHORIZATION) != null)
            throw new AuthException("You cannot log in while you are logged in");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(), request.password()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtils.generateJwtToken(authentication);
        return new AuthorizationResponse(token);
    }

}
