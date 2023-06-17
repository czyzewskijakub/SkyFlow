package pl.ioad.skyflow.logic.user.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationResponse extends SimpleResponse {
    @Schema(description = "JWT Token")
    private String token;

    public AuthorizationResponse(int status, String message, String token) {
        super(status, message);
        this.token = token;
    }
}