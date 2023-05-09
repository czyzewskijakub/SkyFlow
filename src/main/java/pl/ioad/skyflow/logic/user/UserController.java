package pl.ioad.skyflow.logic.user;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ioad.skyflow.database.model.User;
import pl.ioad.skyflow.logic.user.dto.Mapper;
import pl.ioad.skyflow.logic.user.dto.UserDto;
import pl.ioad.skyflow.logic.user.payload.request.LoginRequest;
import pl.ioad.skyflow.logic.user.payload.request.RegisterRequest;
import pl.ioad.skyflow.logic.user.payload.response.AuthorizationResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    private static final Mapper mapper = new Mapper();


    /**
     * register new User
     *
     * @param registerRequest - {@link RegisterRequest}
     * @param httpServletRequest - HTTP Servlet Request
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
                                                @Valid @RequestBody RegisterRequest registerRequest,
                                            @ApiParam(name = "HTTP Servlet Request",
                                                    value = "Request information for HTTP servlets")
                                          HttpServletRequest httpServletRequest) {
        String headerToken = "";
        User user = new User();
        String headerAuth = httpServletRequest.getHeader(AUTHORIZATION);
        if (headerAuth != null && !headerAuth.equals("")) {
            headerToken = httpServletRequest.getHeader(AUTHORIZATION).substring("Bearer ".length());
            user = userService.getUserByToken(headerToken);
            if (!headerToken.isBlank() && !user.getIsAdmin())
                System.out.println("U can't register new account while u are logged in");
        }

        if (registerRequest.getIsAdmin() && !headerToken.isBlank() && user.getIsAdmin()) {
            return ResponseEntity.ok().body(mapper.mapUser(
                    userService.registerAdmin(registerRequest.getFirstName(),
                    registerRequest.getLastName(),
                    registerRequest.getEmail(),
                    registerRequest.getPassword(),
                    registerRequest.getPictureUrl())));
        } else if (Boolean.TRUE.equals(registerRequest.getIsAdmin())) {
            System.out.println("U are not authorized to register new admin");
        }
        return ResponseEntity.ok().body(mapper.mapUser(
                    userService.register(registerRequest.getFirstName(),
                    registerRequest.getLastName(),
                    registerRequest.getEmail(),
                    registerRequest.getPassword(),
                    registerRequest.getPictureUrl())));
    }

    /**
     * login to the user
     *
     * @param loginRequest - {@link LoginRequest}
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
                                        @Valid @RequestBody LoginRequest loginRequest,
                                                       @ApiParam(name = "HTTP Servlet Request",
                                        value = "Request information for HTTP servlets")
                                        HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getHeader(AUTHORIZATION) != null)
            System.out.println("U can't log in while u are logged in");

        String token = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok().body(new AuthorizationResponse(token, mapper.mapUser(userService.getUserByToken(token))));
    }




}
