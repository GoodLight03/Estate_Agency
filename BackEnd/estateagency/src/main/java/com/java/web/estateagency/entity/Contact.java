package com.java.web.estateagency.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;

    private String title;

    private String content;

    private String reply;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone = "GMT+7")
    private Date dayContact;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone = "GMT+7")
    private Date dayReply;

    private String status;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
