package pl.ioad.skyflow.logic.user.payload.request;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(notes = "User username", example = "example@gmail.com")
    @NonNull
    @NotBlank
    @Email
    private String email;
    @ApiModelProperty(notes = "User password", example = "123abC#")
    @NonNull
    @NotBlank
    private String password;
}
