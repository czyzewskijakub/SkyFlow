package pl.ioad.skyflow.logic.user.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
@Builder
public class UserDataRequest {

    @NotBlank(message = "User first name cannot be blank")
    @NotNull
    @Schema(description = "User first name", example = "John")
    private String firstName;

    @NotBlank(message = "User last name cannot be blank")
    @NotNull
    @Schema(description = "User last name", example = "Smith")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @NotNull
    @Size(min = 8, max = 50)
    @Email(message = "Invalid email address")
    @Schema(description = "User email", example = "example@gmail.com")
    private String email;

    @URL(message = "User profile picture should be URL")
    @Schema(description = "User profile picture Url", example = "https://pl.pinterest.com/pin/327848047887112192/")
    private String pictureUrl;

    @NotBlank(message = "User password cannot be blank")
    @NotNull
    @Size(min = 6, max = 40)
    @Schema(description = "User password", example = "123abC#")
    private String password;
}
