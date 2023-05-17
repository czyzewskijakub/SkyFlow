package pl.ioad.skyflow.logic.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record UserDto (
    @Schema(description = "User first name", example = "John")
    String firstName,

    @Schema(description = "User last name", example = "Smith")
    String lastName,

    @Schema(description = "User email", example = "example@gmail.com")
    String email,

    @Schema(description = "Is user admin or standard user", example = "true")
    Boolean isAdmin,

    @Schema(description = "Profile picture url", example = "https://pl.pinterest.com/pin/327848047887112192/")
    String profilePictureUrl
    ) {
}
