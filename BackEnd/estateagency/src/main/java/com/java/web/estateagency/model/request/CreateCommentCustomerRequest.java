package com.java.web.estateagency.model.request;

import java.util.Date;

import lombok.Data;

@Data
public class CreateCommentCustomerRequest {
    private String content;

    private Date date;

    private Long idCustomer;

    private Long idRoom;


    private Long idfirstUserName;
    private Long idsecondUserName;
}
