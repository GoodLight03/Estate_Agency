package com.java.web.estateagency.model.request;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.java.web.estateagency.entity.Room;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Schema(
        name = "MaintenanceDto",
        description = "Schema to hold Maintenance information"
)
public class CreateMaintenanceRequest {
    @Schema(
            description = "Maintenance name of Estateagency Maintenance", example = "Maintain month 04"
    )
    @NotEmpty(message = "Maintenance name can not be a null or empty")
     private String name;

    @Schema(
            description = "Maintenance date of Estateagency Maintenance"
            //, example = ""
    )
    private Date date;

    @Schema(
            description = "Maintenance price of Estateagency Maintenance", example = "230000"
    )
    @NotEmpty(message = "Maintenance price can not be a null or empty")
    private Long price;

    @Schema(
            description = "Maintenance file of Estateagency Maintenance"
    )
    private MultipartFile file;

    @Schema(
            description = "Room id of Estateagency Room", example = "1"
    )
    @NotEmpty(message = "Room id can not be a null or empty")
    private Long idroom;
}
