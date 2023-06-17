package pl.ioad.skyflow.logic.user;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
import pl.ioad.skyflow.logic.user.dto.Mapper;
import pl.ioad.skyflow.logic.user.dto.UserWithIdDto;
import pl.ioad.skyflow.logic.user.payload.request.LoginRequest;
import pl.ioad.skyflow.logic.user.payload.request.UpdateDataRequest;
import pl.ioad.skyflow.logic.user.payload.request.UserDataRequest;
import pl.ioad.skyflow.logic.user.payload.response.AuthorizationResponse;
import pl.ioad.skyflow.logic.user.payload.response.SimpleResponse;
import pl.ioad.skyflow.logic.user.payload.response.UserResponse;
import pl.ioad.skyflow.logic.user.security.jwt.JwtUtils;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;
    private final Mapper mapper = new Mapper();

    public UserResponse register(UserDataRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
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
        return new UserResponse(
                HttpStatus.CREATED.value(),
                "Successfully registered user account",
                mapper.mapUser(user));

    }

    public UserResponse registerAdmin(UserDataRequest request, HttpServletRequest http) {
        String token = http.getHeader("Authorization");
        if (token == null) {
            throw new ForbiddenException("You are not authorized");
        }
        token = token.substring("Bearer ".length());
        var user = userRepository.findByEmail(jwtUtils.extractUsername(token));
        if (user.isPresent() && !user.get().getIsAdmin()) {
            throw new InvalidBusinessArgumentException("You cannot register new admin as standard user");
        }   else if (userRepository.existsByEmail(request.getEmail())) {
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
                HttpStatus.CREATED.value(),
                "Successfully registered admin user account",
                mapper.mapUser(newUser));
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
        return new AuthorizationResponse(HttpStatus.OK.value(), "Successfully logged in", token);
    }

    public UserResponse update(UpdateDataRequest userData, HttpServletRequest http) {
        User currentUser = validateToken(http);

        if (userRepository.existsByEmail(userData.getEmail())) {
            throw new DuplicatedDataException("User with given email already exists");
        }

        if (userData.getFirstName() != null && !userData.getFirstName().isEmpty()) {
            currentUser.setFirstName(userData.getFirstName());
        }
        if (userData.getLastName() != null && !userData.getLastName().isEmpty()) {
            currentUser.setLastName(userData.getLastName());
        }
        if (userData.getEmail() != null && !userData.getEmail().isEmpty()) {
            currentUser.setEmail(userData.getEmail());
        }
        if (userData.getPassword() != null && !userData.getPassword().isEmpty()) {
            currentUser.setPasswordHash(encoder.encode(userData.getPassword()));
        }
        if (userData.getPictureUrl() != null && !userData.getPictureUrl().isEmpty()) {
            currentUser.setProfilePictureUrl(userData.getPictureUrl());
        }

        userRepository.save(currentUser);

        return new UserResponse(
                HttpStatus.ACCEPTED.value(),
                "User data updated",
                mapper.mapUser(currentUser)
                );
    }

    public SimpleResponse changeUserAccountType(Long userId, boolean isAdmin, HttpServletRequest http) {
        User user = validateToken(http);
        if (!user.getIsAdmin()) {
            throw new ForbiddenException("As a standard user you cannot change account types");
        }
        Optional<User> existingUser = userRepository.findById(userId);

        if (existingUser.isEmpty()) {
            throw new EntityNotFoundException("User with given ID does not exist");
        }

        existingUser.get().setIsAdmin(isAdmin);
        userRepository.save(existingUser.get());
        String message;
        if (isAdmin) {
            message = "User account type changed from standard user to admin";
        } else {
            message = "User account type changed from admin to standard user";
        }

        return new SimpleResponse(
                HttpStatus.ACCEPTED.value(),
                message
                );
    }

    public List<UserWithIdDto> getAllUsers(HttpServletRequest http) {
        if (!validateToken(http).getIsAdmin()) {
            throw new ForbiddenException("You cannot display all users");
        }
        return userRepository.findAll().stream().map(mapper::mapUserWithId).toList();
    }

    protected User validateToken(HttpServletRequest http) {
        String token = http.getHeader("Authorization");
        if (token == null) {
            throw new ForbiddenException("You have to be logged-in to your account to change data");
        }
        token = token.substring("Bearer ".length());
        var user = userRepository.findByEmail(jwtUtils.extractUsername(token));
        if (user.isPresent())
            return user.get();
        throw new ForbiddenException("You are not authorized");
    }

}
