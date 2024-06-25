package com.java.web.estateagency.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChangePasswordRequest {
    
    private String username;

    private String oldPassword;

    private String newPassword;

    private String confilmPassword;
    
}
