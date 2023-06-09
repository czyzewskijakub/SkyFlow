package pl.ioad.skyflow.logic.user.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.NonNull;


public record LoginRequest(
        @Schema(description = "User username", example = "example@gmail.com")
        @NonNull
        @NotBlank
        @Email
        String email,

        @Schema(description = "User password", example = "123abC#")
        @NonNull
        @NotBlank
        String password
) {
}
