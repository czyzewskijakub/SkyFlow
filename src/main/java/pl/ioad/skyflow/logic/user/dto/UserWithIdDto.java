package pl.ioad.skyflow.logic.user.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserWithIdDto extends UserDto {

    @NotNull
    private Long userId;

    @Builder(builderMethodName = "UserWithIdBuilder")
    public UserWithIdDto(Long userId, String firstName, String lastName, String email,
                          Boolean isAdmin, String profilePictureUrl) {
        super(firstName, lastName, email, isAdmin, profilePictureUrl);
        this.userId = userId;
    }
}
