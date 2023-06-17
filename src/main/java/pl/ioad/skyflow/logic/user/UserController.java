package pl.ioad.skyflow.logic.user;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ioad.skyflow.logic.user.dto.UserDto;
import pl.ioad.skyflow.logic.user.payload.request.LoginRequest;
import pl.ioad.skyflow.logic.user.payload.request.RegisterRequest;
import pl.ioad.skyflow.logic.user.payload.response.AuthorizationResponse;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    /**
     * register new User
     *
     * @param request - {@link RegisterRequest}
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
    public ResponseEntity<UserDto> register(
            @Parameter(description = "Registration request body", required = true)
            @Valid @RequestBody RegisterRequest request) {
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
    public ResponseEntity<UserDto> registerAdmin(
            @Parameter(description = "Admin registration request body", required = true)
            @Valid @RequestBody RegisterRequest request,
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
}
