package pl.ioad.skyflow.logic.user;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(value = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Registration succeed"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 401, message = "U are not authorized"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 406, message = "Not allowed user registration data")
    })
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@ApiParam(name = "Registration request body",
                                                        value = "Body of the request")
                                                @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok().body(userService.register(request));
    }

    @PostMapping("/register/admin")
    public ResponseEntity<UserDto> registerAdmin(@ApiParam(name = "Admin registration request body",
                                                            value = "Body of the request")
                                                 @Valid @RequestBody RegisterRequest request,
                                                 @ApiParam(name = "HTTP Servlet Request",
                                                         value = "Request information for HTTP servlets")
                                                 HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(userService.registerAdmin(request, httpServletRequest));
    }


    /**
     * login to the user
     *
     * @param request - {@link LoginRequest}
     * @param httpServletRequest - HTTP Servlet Request
     * @return - {@link AuthorizationResponse}
     */
    @ApiOperation(value = "Login to the user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login succeed"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 401, message = "U are not authorized"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthorizationResponse> login(@ApiParam(name = "Login request", value = "Login request information")
                                        @Valid @RequestBody LoginRequest request,
                                                       @ApiParam(name = "HTTP Servlet Request",
                                        value = "Request information for HTTP servlets")
                                        HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(userService.login(request, httpServletRequest));
    }
}
