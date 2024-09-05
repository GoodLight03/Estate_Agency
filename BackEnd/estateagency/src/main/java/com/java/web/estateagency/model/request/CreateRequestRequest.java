package com.java.web.estateagency.model.request;

import java.util.Date;

//import com.java.web.estateagency.entity.Requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(
        name = "RequestDto",
        description = "Request to hold Request information"
)
public class CreateRequestRequest {
    @Schema(
            description = "Requests Id of Estateagency Requests", example = "4"
    )
     private long id;

    @Schema(
            description = "User Id User of Estateagency User", example = "4"
    )
    @NotEmpty(message = "User Id can not be a null or empty")
    private long iduser;

    @Schema(
            description = "Room Id User of Estateagency Room", example = "4"
    )
    @NotEmpty(message = "Room Id can not be a null or empty")
    private long idroom;

    @Schema(
            description = "Requests Id of Estateagency Requests", example = "4"
    )
    @NotEmpty(message = "Requests Id can not be a null or empty")
    private long idRequests;

    @Schema(
            description = "Requests status of Estateagency Requests", example = "Waiting"
    )
    @NotEmpty(message = "Requests status can not be a null or empty")
    private String status;

    @Schema(
            description = "Requests description of Estateagency Requests", example = "Help"
    )
    @NotEmpty(message = "Requests description can not be a null or empty")
    private String description;

    @Schema(
            description = "Requests browse of Estateagency Requests", example = "true"
    )
    @NotEmpty(message = "Requests browse can not be a null or empty")
    private Boolean browse;

    @Schema(
            description = "Requests Id of Estateagency Requests"
            //, example = "4"
    )
    private Date date;
}
