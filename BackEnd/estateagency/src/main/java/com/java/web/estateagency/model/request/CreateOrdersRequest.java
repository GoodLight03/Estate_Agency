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
        name = "OrderDto",
        description = "Order to hold Message information"
)
public class CreateOrdersRequest {
    @Schema(
            description = "Room Id of Estateagency Room", example = "4"
    )
    @NotEmpty(message = "Room Id can not be a null or empty")
    private Long idRoom;

    @Schema(
            description = "Agent Id of Estateagency User", example = "4"
    )
    @NotEmpty(message = "Agent Id can not be a null or empty")
    private Long idAgent;

    @Schema(
            description = "Order Id of Estateagency Order", example = "Waiting"
    )
    @NotEmpty(message = "Order Status can not be a null or empty")
    private String status;

    @Schema(
            description = "Order Name Customer of Estateagency Order", example = "Quang"
    )
    @NotEmpty(message = "Order Name Customer can not be a null or empty")
    private String nameCustomer;
}
