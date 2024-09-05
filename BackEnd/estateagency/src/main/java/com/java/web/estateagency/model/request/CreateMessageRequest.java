package com.java.web.estateagency.model.request;

import java.io.Serializable;
import java.util.Date;

import com.java.web.estateagency.entity.Chat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(
        name = "MessageDto",
        description = "Schema to hold Message information"
)
public class CreateMessageRequest implements Serializable{
    @Schema(
            description = "Message senderEmail of Estateagency Message", example = "Quang"
    )
    @NotEmpty(message = "Message senderEmail can not be a null or empty")
    private String senderEmail;

    @Schema(
            description = "Message time of Estateagency Message"
    )
    private Date time = new Date(System.currentTimeMillis());

    @Schema(
            description = "Message replymessage of Estateagency Message", example = "Hello"
    )
    @NotEmpty(message = "Message replymessage can not be a null or empty")
    private String replymessage;

    @Schema(
            description = "Chat Id of Estateagency Chat", example = "4"
    )
    @NotEmpty(message = "Chat Id can not be a null or empty")
    private Long idchat;
}
