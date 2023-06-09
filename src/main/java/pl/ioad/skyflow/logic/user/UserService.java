package pl.ioad.skyflow.logic.user;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.google.common.base.Strings;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
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
import pl.ioad.skyflow.logic.exception.type.DuplicatedDataException;
import pl.ioad.skyflow.logic.exception.type.ForbiddenException;
import pl.ioad.skyflow.logic.exception.type.InvalidBusinessArgumentException;
import pl.ioad.skyflow.logic.user.dto.UserMapper;
import pl.ioad.skyflow.logic.user.payload.request.LoginRequest;
import pl.ioad.skyflow.logic.user.payload.request.UpdateDataRequest;
import pl.ioad.skyflow.logic.user.payload.request.UserDataRequest;
import pl.ioad.skyflow.logic.user.payload.response.AuthorizationResponse;
import pl.ioad.skyflow.logic.user.payload.response.SimpleResponse;
import pl.ioad.skyflow.logic.user.payload.response.UserResponse;
import pl.ioad.skyflow.logic.user.security.jwt.JwtUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    public UserResponse register(UserDataRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ForbiddenException("Email is taken");
        }
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .passwordHash(encoder.encode(request.getPassword()))
                .profilePictureUrl(request.getPictureUrl())
                .isAdmin(false)
                .build();
        userRepository.save(user);

        return new UserResponse(
                CREATED.value(),
                "Successfully registered user account",
                userMapper.mapUser(user));

    }

    public UserResponse registerAdmin(UserDataRequest request, HttpServletRequest http) {
        String token = http.getHeader("Authorization");
        if (token == null) {
            throw new ForbiddenException("You are not authorized");
        }
        token = token.substring("Bearer ".length());
        var user = userRepository.findByEmail(jwtUtils.extractUsername(token));
        if (user.isPresent() && !user.get().isAdmin()) {
            throw new InvalidBusinessArgumentException("You cannot register new admin as standard user");
        } else if (userRepository.existsByEmail(request.getEmail())) {
            throw new ForbiddenException("Email is taken");
        }

        User newUser = userRepository.save(User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .passwordHash(encoder.encode(request.getPassword()))
                .profilePictureUrl(request.getPictureUrl())
                .isAdmin(true)
                .build());
        return new UserResponse(
                CREATED.value(),
                "Successfully registered admin user account",
                userMapper.mapUser(newUser));
    }

    public AuthorizationResponse login(LoginRequest request, HttpServletRequest httpServletRequest) {
        if (!userRepository.existsByEmail(request.email())) {
            throw new EntityNotFoundException("User with given data does not exist");
        }
        if (httpServletRequest.getHeader(AUTHORIZATION) != null) {
            throw new AuthException("You cannot log in while you are logged in");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(), request.password()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtils.generateJwtToken(authentication);
        return new AuthorizationResponse(OK.value(), "Successfully logged in", token);
    }

    public UserResponse update(UpdateDataRequest userData, HttpServletRequest http) {
        User currentUser = validateToken(http);

        if (userRepository.existsByEmail(userData.getEmail())) {
            throw new DuplicatedDataException("User with given email already exists");
        }

        if (!Strings.isNullOrEmpty(userData.getFirstName())) {
            currentUser.setFirstName(userData.getFirstName());
        }
        if (!Strings.isNullOrEmpty(userData.getLastName())) {
            currentUser.setLastName(userData.getLastName());
        }
        if (!Strings.isNullOrEmpty(userData.getEmail())) {
            currentUser.setEmail(userData.getEmail());
        }
        if (!Strings.isNullOrEmpty(userData.getPassword())) {
            currentUser.setPasswordHash(encoder.encode(userData.getPassword()));
        }
        if (!Strings.isNullOrEmpty(userData.getPictureUrl())) {
            currentUser.setProfilePictureUrl(userData.getPictureUrl());
        }

        userRepository.save(currentUser);

        return new UserResponse(
                ACCEPTED.value(),
                "User data updated",
                userMapper.mapUser(currentUser)
        );
    }

    public SimpleResponse changeUserAccountType(Long userId, boolean isAdmin) {
        Optional<User> existingUser = userRepository.findById(userId);

        if (existingUser.isEmpty()) {
            throw new EntityNotFoundException("User with given ID does not exist");
        }

        existingUser.get().setAdmin(isAdmin);
        userRepository.save(existingUser.get());
        String message = isAdmin ? "User account type changed from standard user to admin"
                : "User account type changed from admin to standard user";

        return new SimpleResponse(
                ACCEPTED.value(),
                message
        );
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    protected User validateToken(HttpServletRequest http) {
        if (http == null) {
            throw new ForbiddenException("Authorization header is null");
        }
        String token = http.getHeader("Authorization");
        if (token == null) {
            throw new ForbiddenException("You have to be logged-in to your account to change data");
        }
        token = token.substring("Bearer ".length());
        var user = userRepository.findByEmail(jwtUtils.extractUsername(token));
        if (user.isPresent()) {
            return user.get();
        }
        throw new ForbiddenException("You are not authorized");
    }

}
