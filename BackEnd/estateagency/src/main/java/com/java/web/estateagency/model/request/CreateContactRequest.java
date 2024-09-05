package com.java.web.estateagency.model.request;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Schema(
        name = "ContactDto",
        description = "Schema to hold Contact information"
)
public class CreateContactRequest {
    @Schema(
            description = "Contacts email of Estateagency Contacts", example = "nguyenquang@gmailcom"
    )
    @NotEmpty(message = "Contacts email can not be a null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @Schema(
            description = "Contacts title of Estateagency Contacts", example = "Help"
    )
    @NotEmpty(message = "Contacts title can not be a null or empty")
    private String title;

    @Schema(
            description = "Contacts content of Estateagency Contacts", example = "Support"
    )
    @NotEmpty(message = "Contacts content can not be a null or empty")
    private String content;

    @Schema(
            description = "Contacts reply of Estateagency Contacts", example = "Ok"
    )
    private String reply;

    @Schema(
            description = "Contacts dayContact of Estateagency Contacts"
            //, example = ""
    )
    private Date dayContact;

    @Schema(
            description = "Contacts dayReply of Estateagency Contacts"
            //, example = ""
    )
    private Date dayReply;

    @Schema(
            description = "Contacts status of Estateagency Contacts", example = "Waiting"
    )
    private String status;

    @Schema(
            description = "Contacts iduser of Estateagency User", example = "1"
    )
    @NotEmpty(message = "Contacts iduser can not be a null or empty")
    private long iduser;
}
