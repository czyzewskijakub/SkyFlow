package pl.ioad.skyflow.logic.user.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SimpleResponse {
    @Schema(description = "status code")
    private int status;

    @Schema(description = "response message")
    private String message;
}
