package com.java.web.estateagency.model.request;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(
        name = "BillDto",
        description = "Schema to hold Bill information"
)
public class CreateBillRequest {

    @Schema(
            description = "Bill Name of Estateagency Bill", example = "Bill Month 09"
    )
    @NotEmpty(message = "Bill Name can not be a null or empty")
    private String name;

    @Schema(
            description = "Contract Id of Estateagency Bill", example = "1"
    )
    @NotEmpty(message = "Contract Id can not be a null or empty")
    private Long idcontact;

    @Schema(
            description = "Bill Start Date of Estateagency Bill"
            //, example = ""
    )
    @NotEmpty(message = "Bill Start Date can not be a null or empty")
    private Date start;

    @Schema(
            description = "Bill End Date of Estateagency Bill"
            //, example = ""
    )
    @NotEmpty(message = "Bill End Date can not be a null or empty")
    private Date end;
}
