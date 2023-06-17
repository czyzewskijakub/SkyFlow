package pl.ioad.skyflow.logic.user.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
public class UpdateDataRequest {
    @Schema(description = "User first name", example = "John")
    private String firstName;

    @Schema(description = "User last name", example = "Smith")
    private String lastName;

    @Size(min = 8, max = 50, message = "Email address length should be between 6 and 50 characters")
    @Email(message = "Invalid email address")
    @Schema(description = "User email", example = "example@gmail.com")
    private String email;

    @URL(message = "User profile picture should be URL")
    @Schema(description = "User profile picture Url", example = "https://pl.pinterest.com/pin/327848047887112192/")
    private String pictureUrl;

    @Size(min = 6, max = 40, message = "User password length should be more than 6")
    @Schema(description = "User password", example = "123abC#")
    private String password;
}
