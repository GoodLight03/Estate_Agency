package com.java.web.estateagency.model.request;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private Long idRoom;
    private Long idAgent;
    private String status;
}
