package com.java.web.estateagency.model.request;

import java.util.Date;

import com.java.web.estateagency.entity.Room;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class CreateRequestRequest {
     private long id;

    private long iduser;

    private long idroom;

    private String status;

    private String description;

    private Boolean browse;

    private Date date;
}
