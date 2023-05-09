package pl.ioad.skyflow.logic.user.payload.request;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(notes = "User first name", example = "John")
    private String firstName;

    @NotBlank
    @NonNull
    @ApiModelProperty(notes = "User last name", example = "Smith")
    private String lastName;

    @NotBlank
    @NonNull
    @Size(max = 50)
    @Email
    @ApiModelProperty(notes = "User email", example = "example@gmail.com")
    private String email;

    @ApiModelProperty(notes = "Is user admin", example = "false")
    private Boolean isAdmin = false;

    @NotBlank
    @NonNull
    @ApiModelProperty(notes = "User profile picture Url", example = "https://pl.pinterest.com/pin/327848047887112192/")
    private String pictureUrl;

    @NotBlank
    @NonNull
    @Size(min = 6, max = 40)
    @ApiModelProperty(notes = "User password", example = "123abC#")
    private String password;
}
