package pl.ioad.skyflow.logic.user;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ioad.skyflow.logic.user.dto.UserDto;
import pl.ioad.skyflow.logic.user.payload.request.LoginRequest;
import pl.ioad.skyflow.logic.user.payload.request.UpdateDataRequest;
import pl.ioad.skyflow.logic.user.payload.request.UserDataRequest;
import pl.ioad.skyflow.logic.user.payload.response.AuthorizationResponse;
import pl.ioad.skyflow.logic.user.payload.response.UserResponse;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    /**
     * register new User
     *
     * @param request - {@link UserDataRequest}
     * @return {@link UserDto}
     */
    @Operation(summary = "Register new user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration succeed"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "401", description = "U are not authorized"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "406", description = "Not allowed user registration data")
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Parameter(description = "Registration request body", required = true)
            @Valid @RequestBody UserDataRequest request) {
        return ResponseEntity.ok().body(userService.register(request));
    }

    @Operation(summary = "Register new administrator account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration succeed"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "401", description = "U are not authorized"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "406", description = "Not allowed user registration data")
    })
    @PostMapping("/register/admin")
    public ResponseEntity<UserResponse> registerAdmin(
            @Parameter(description = "Admin registration request body", required = true)
            @Valid @RequestBody UserDataRequest request,
            @Parameter(description = "HTTP Servlet Request", required = true)
            HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(userService.registerAdmin(request, httpServletRequest));
    }


    /**
     * login to the user
     *
     * @param request            - {@link LoginRequest}
     * @param httpServletRequest - HTTP Servlet Request
     * @return - {@link AuthorizationResponse}
     */
    @Operation(summary = "Login to the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login succeed"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "401", description = "U are not authorized"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthorizationResponse> login(
            @Parameter(description = "Login request", required = true)
            @Valid @RequestBody LoginRequest request,
            @Parameter(description = "HTTP Servlet Request", required = true)
            HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(userService.login(request, httpServletRequest));
    }

    @Operation(summary = "Update user data")
    @PutMapping("/update")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "401", description = "U are not authorized"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "406", description = "Not allowed user update data")
    })
    public ResponseEntity<UserResponse> updateUserData(
            @RequestParam(name = "userId") Long userId,
            @RequestBody @Valid UpdateDataRequest userData) {
        return ResponseEntity.accepted().body(userService.update(userId, userData));
    }

}
