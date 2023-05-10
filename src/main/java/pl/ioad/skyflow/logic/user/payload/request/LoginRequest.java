package pl.ioad.skyflow.logic.user.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;


@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
public class LoginRequest {
    @Schema(description = "User username", example = "example@gmail.com")
    @NonNull
    @NotBlank
    @Email
    private String email;

    @Schema(description = "User password", example = "123abC#")
    @NonNull
    @NotBlank
    private String password;
}
