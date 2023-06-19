package pl.ioad.skyflow.logic.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    @Schema(description = "User first name", example = "John")
    private String firstName;

    @Schema(description = "User last name", example = "Smith")
     private String lastName;

    @Schema(description = "User email", example = "example@gmail.com")
    private String email;

    @Schema(description = "Is user admin or standard user", example = "true")
    private boolean isAdmin;

    @Schema(description = "Profile picture url", example = "https://pl.pinterest.com/pin/327848047887112192/")
    private String profilePictureUrl;
}
