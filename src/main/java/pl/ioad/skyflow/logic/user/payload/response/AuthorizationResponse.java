package pl.ioad.skyflow.logic.user.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthorizationResponse {
    @Schema(description = "JWT Token", example =
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJta0BvMi5wbCIsIml" +
                    "hdCI6MTY3MDc2MDk0OSwiZXhwIjoxNjcwODQ3MzQ5fQ.vgQ" +
                    "-aLC1uPv7NixiqHET2lLxjZaJY4W-z0lU9gZ6Z29dKeDrO66FG" +
                    "w8yxC4mkpm9LhzEbyGpcy8YREJNuIutQA")
    private String token;
}