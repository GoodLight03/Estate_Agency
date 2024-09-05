package com.java.web.estateagency.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "ChatDto",
        description = "Schema to hold Chat information"
)
public class CreateChatRoomRequest {
    @Schema(
            description = "Id first UserName of the Chat", example = "1"
    )
    @NotEmpty(message = "Id first UserName can not be a null or empty")
    private Long idfirstUserName;

    @Schema(
            description = "Id second UserName of the Chat", example = "1"
    )
    @NotEmpty(message = "Id second UserName can not be a null or empty")
    private Long idsecondUserName;
}
