package com.java.web.estateagency.model.request;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(
        name = "CommentDto",
        description = "Schema to hold Comments information"
)
public class CreateCommentCustomerRequest {
    @Schema(
            description = "Comments content of Estateagency Comments", example = "Good Room!"
    )
    @NotEmpty(message = "Comments content can not be a null or empty")
    private String content;

    @Schema(
            description = "Comments date of Estateagency Comments"
            //, example = ""
    )
    @NotEmpty(message = "Comments date can not be a null or empty")
    private Date date;

    @Schema(
            description = "Id Customer of Estateagency User", example = "1"
    )
    @NotEmpty(message = "Id Customer can not be a null or empty")
    private Long idCustomer;

    @Schema(
            description = "Id Room of Estateagency Room", example = "9"
    )
    @NotEmpty(message = "Id Room can not be a null or empty")
    private Long idRoom;

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
