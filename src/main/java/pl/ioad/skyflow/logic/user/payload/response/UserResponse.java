package pl.ioad.skyflow.logic.user.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import pl.ioad.skyflow.logic.user.dto.UserDto;

@Getter
@Setter
public class UserResponse extends SimpleResponse {
    @Schema(description = "User data")
    private UserDto user;

    public UserResponse(int status, String message, @Nullable UserDto user) {
        super(status, message);
        this.user = user;
    }
}
