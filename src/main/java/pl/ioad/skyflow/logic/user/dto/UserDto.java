package pl.ioad.skyflow.logic.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    @Schema(description = "User first name", example = "John")
    private String firstName;

    @Schema(description = "User last name", example = "Smith")
    private String lastName;

    @Schema(description = "User email", example = "example@gmail.com")
    private String email;

    @Schema(description = "Is user admin or standard user", example = "true")
    private Boolean isAdmin;

    @Schema(description = "Profile picture url", example = "https://pl.pinterest.com/pin/327848047887112192/")
    private String profilePictureUrl;
}
