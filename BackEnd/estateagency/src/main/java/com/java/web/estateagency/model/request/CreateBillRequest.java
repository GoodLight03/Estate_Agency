package com.java.web.estateagency.model.request;

import java.util.Date;

import lombok.Data;

@Data
public class CreateBillRequest {
    private String name;

    private Long idcontact;

    private Date start;

    private Date end;
}
