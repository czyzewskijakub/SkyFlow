package pl.ioad.skyflow.logic.user.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
public class RegisterRequest {

    @NotBlank
    @NonNull
    @Schema(description = "User first name", example = "John")
    private String firstName;

    @NotBlank
    @NonNull
    @Schema(description = "User last name", example = "Smith")
    private String lastName;

    @NotBlank
    @NonNull
    @Size(max = 50)
    @Email
    @Schema(description = "User email", example = "example@gmail.com")
    private String email;


    @NotBlank
    @NonNull
    @Schema(description = "User profile picture Url", example = "https://pl.pinterest.com/pin/327848047887112192/")
    private String pictureUrl;

    @NotBlank
    @NonNull
    @Size(min = 6, max = 40)
    @Schema(description = "User password", example = "123abC#")
    private String password;
}
