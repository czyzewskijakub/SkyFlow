package pl.ioad.skyflow.logic.user;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ioad.skyflow.database.model.User;
import pl.ioad.skyflow.logic.user.dto.UserDto;
import pl.ioad.skyflow.logic.user.payload.request.LoginRequest;
import pl.ioad.skyflow.logic.user.payload.request.UpdateDataRequest;
import pl.ioad.skyflow.logic.user.payload.request.UserDataRequest;
import pl.ioad.skyflow.logic.user.payload.response.AuthorizationResponse;
import pl.ioad.skyflow.logic.user.payload.response.SimpleResponse;
import pl.ioad.skyflow.logic.user.payload.response.UserResponse;

import java.util.List;

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
            @ApiResponse(responseCode = "201", description = "Registration succeed", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "status": 201,
                                                  "message": "Successfully registered user account",
                                                  "user": {
                                                    "firstName": "John",
                                                    "lastName": "Smith",
                                                    "email": "example@gmail.com",
                                                    "profilePictureUrl": "https://pl.pinterest.com/pin/327848047887112192/",
                                                    "admin": false
                                                  }
                                                """
                    ))
            ),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 400,
                                                  "exception": "Exception",
                                                  "message": "Bad request"
                                                }
                                                """
                    ))
            ),
            @ApiResponse(responseCode = "401", description = "U are not authorized", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 401,
                                                  "exception": "Exception",
                                                  "message": "Unauthorized"
                                                }
                                                """
                    ))
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 403,
                                                  "exception": "Exception",
                                                  "message": "Forbidden"
                                                }
                                                """
                    ))
            ),
            @ApiResponse(responseCode = "406", description = "Not allowed user registration data",  content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 406,
                                                  "exception": "Exception",
                                                  "message": "Unaccepted"
                                                }
                                                """
                    ))
            )
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Parameter(description = "Registration request body", required = true)
            @Valid @RequestBody UserDataRequest request) {
        return new ResponseEntity<>(
                userService.register(request),
                HttpStatus.CREATED
        );
    }


    @Operation(summary = "Register new administrator account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registration succeed", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "status": 201,
                                                  "message": "Successfully registered admin account",
                                                  "user": {
                                                    "firstName": "John",
                                                    "lastName": "Smith",
                                                    "email": "example@gmail.com",
                                                    "profilePictureUrl": "https://pl.pinterest.com/pin/327848047887112192/",
                                                    "admin": true
                                                  }
                                                """
                    ))
            ),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 400,
                                                  "exception": "Exception",
                                                  "message": "Bad request"
                                                }
                                                """
                    ))
            ),
            @ApiResponse(responseCode = "401", description = "U are not authorized", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 401,
                                                  "exception": "Exception",
                                                  "message": "Unauthorized"
                                                }
                                                """
                    ))
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 403,
                                                  "exception": "Exception",
                                                  "message": "Forbidden"
                                                }
                                                """
                    ))
            ),
            @ApiResponse(responseCode = "406", description = "Not allowed user registration data",  content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 406,
                                                  "exception": "Exception",
                                                  "message": "Unaccepted"
                                                }
                                                """
                    ))
            )
    })
    @PostMapping("/register/admin")
    public ResponseEntity<UserResponse> registerAdmin(
            @Parameter(description = "Admin registration request body", required = true)
            @Valid @RequestBody UserDataRequest request,
            @Parameter(description = "HTTP Servlet Request", required = true)
            HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(
                userService.registerAdmin(request, httpServletRequest),
                HttpStatus.CREATED
        );
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
            @ApiResponse(responseCode = "200", description = "Login succeed", content = @Content(
                    schema = @Schema(example = """
                            {
                              "status": 200,
                              "message": "Successfully logged in",
                              "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlQGdtYWlsLmNvbSIsImlhdCI6MTY4NzAwNDAzNywiZXhwIjoxNjg3MDkwNDM3fQ.yFRMblX-se_dMIWICnSPVaoKviH97aCcnjQw7ISb_hA"
                            }"""
                    ))
            ),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 400,
                                                  "exception": "Exception",
                                                  "message": "Bad request"
                                                }
                                                """
                    ))
            ),
            @ApiResponse(responseCode = "401", description = "U are not authorized", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 401,
                                                  "exception": "Exception",
                                                  "message": "Unauthorized"
                                                }
                                                """
                    ))
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 403,
                                                  "exception": "Exception",
                                                  "message": "Forbidden"
                                                }
                                                """
                    ))
            ),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 404,
                                                  "exception": "Exception",
                                                  "message": "Not found"
                                                }
                                                """
                    ))
            )
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
            @ApiResponse(responseCode = "202", description = "User updated", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "status": 202,
                                                  "message": "User updated",
                                                  "user": {
                                                    "firstName": "John",
                                                    "lastName": "Smith",
                                                    "email": "example@gmail.com",
                                                    "profilePictureUrl": "https://pl.pinterest.com/pin/327848047887112192/",
                                                    "admin": false
                                                  }
                                                """
                    ))
            ),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 400,
                                                  "exception": "Exception",
                                                  "message": "Bad request"
                                                }
                                                """
                    ))
            ),
            @ApiResponse(responseCode = "401", description = "U are not authorized", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 401,
                                                  "exception": "Exception",
                                                  "message": "Unauthorized"
                                                }
                                                """
                    ))
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 403,
                                                  "exception": "Exception",
                                                  "message": "Forbidden"
                                                }
                                                """
                    ))
            ),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 404,
                                                  "exception": "Exception",
                                                  "message": "Not found"
                                                }
                                                """
                    ))
            ),
            @ApiResponse(responseCode = "406", description = "Data not allowed",  content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 406,
                                                  "exception": "Exception",
                                                  "message": "Unaccepted"
                                                }
                                                """
                    ))
            )
    })
    public ResponseEntity<UserResponse> updateUserData(
            @RequestBody @Valid UpdateDataRequest userData,
            HttpServletRequest httpServletRequest) {
        return ResponseEntity.accepted().body(userService.update(userData, httpServletRequest));
    }

    @Operation(summary = "Change user account type")
    @PutMapping("/type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Account type updated", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "status": 202,
                                                  "message": "User account type changed from admin to standard user"
                                                }
                                               """
                    ))
            ),
            @ApiResponse(responseCode = "401", description = "U are not authorized", content = @Content(
            schema = @Schema(example = """
                                                {
                                                  "httpStatus": 401,
                                                  "exception": "Exception",
                                                  "message": "Unauthorized"
                                                }
                                                """)
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 403,
                                                  "exception": "Exception",
                                                  "message": "Forbidden"
                                                }
                                                """
                    ))
            ),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 404,
                                                  "exception": "Exception",
                                                  "message": "Not found"
                                                }
                                                """
                    ))
            ),
    })
    public ResponseEntity<SimpleResponse> changeUserAccountType(@RequestParam Long userId,
                                                                @RequestParam boolean isAdmin) {
        return ResponseEntity.accepted().body(userService.changeUserAccountType(userId, isAdmin));
    }

    @Operation(summary = "Retrieve all existing users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All users retrieved", content = @Content(
                    schema = @Schema(example = """
                                            [
                                                {
                                                  "userId": 1,
                                                  "firstName": "John",
                                                  "lastName": "Smith",
                                                  "email": "example@gmail.com",
                                                  "profilePictureUrl": "https://pl.pinterest.com/pin/327848047887112192/",
                                                  "admin": false
                                                }
                                            ]
                                               """
                    ))
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(
                    schema = @Schema(example = """
                                                {
                                                  "httpStatus": 403,
                                                  "exception": "Exception",
                                                  "message": "Forbidden"
                                                }
                                                """
                    ))
            ),
    })
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


}
