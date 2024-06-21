package com.java.web.estateagency.model.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class CreateContactRequest {
    private String email;

    private String title;

    private String content;

    private String reply;

    private Date dayContact;

    private Date dayReply;

    private String status;

    private long iduser;
}
