package com.java.web.estateagency.model.request;

import java.io.Serializable;
import java.util.Date;

import com.java.web.estateagency.entity.Chat;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class CreateMessageRequest implements Serializable{
    private String senderEmail;
    private Date time = new Date(System.currentTimeMillis());
    private String replymessage;

   
    private Long idchat;
}
