package com.java.web.estateagency.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrdersRequest {
    private Long idRoom;
    private Long idAgent;
    private String status;
    private String nameCustomer;
}
