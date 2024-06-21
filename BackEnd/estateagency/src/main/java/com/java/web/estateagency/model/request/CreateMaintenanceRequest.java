package com.java.web.estateagency.model.request;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.java.web.estateagency.entity.Room;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class CreateMaintenanceRequest {
     private String name;

    private Date date;

    private Long price;

    private MultipartFile file;

    private Long idroom;
}
