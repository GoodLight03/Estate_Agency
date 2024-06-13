package com.java.web.estateagency.service;

import java.util.List;

import com.java.web.estateagency.entity.User;
import com.java.web.estateagency.model.request.ChangePasswordRequest;
import com.java.web.estateagency.model.request.CreateUserRequest;
import com.java.web.estateagency.model.request.UpdateProfileRequest;

public interface UserService {
    
    void register(CreateUserRequest request);

    User getUserByUsername(String username);

    User updateUser(UpdateProfileRequest request);

    void changePassword(ChangePasswordRequest request);

    List<User> getListAgent();

    List<User> getAll();

    User getUsserId(Long id);

}
