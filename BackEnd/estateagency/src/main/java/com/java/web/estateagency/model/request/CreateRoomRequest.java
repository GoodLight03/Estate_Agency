package com.java.web.estateagency.model.request;

import org.springframework.web.multipart.MultipartFile;

import com.java.web.estateagency.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomRequest {
    
    private String name;

    private Float price;

    private String address;

    private String describe;

    private String state;

    private MultipartFile img;

    private boolean enabled;

    private Long idAgent;
}
