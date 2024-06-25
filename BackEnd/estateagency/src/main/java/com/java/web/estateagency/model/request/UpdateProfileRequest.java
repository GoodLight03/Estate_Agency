package com.java.web.estateagency.model.request;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {
    private Long id;

    private String username;

    private String fullname;

    private String email;

    private String state;

    private String address;

    private String phone;

     private MultipartFile img;
}
