package com.java.web.estateagency.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.java.web.estateagency.entity.User;
import com.java.web.estateagency.model.request.ChangePasswordRequest;
import com.java.web.estateagency.model.request.CreateUserRequest;
import com.java.web.estateagency.model.request.UpdateProfileRequest;
import com.java.web.estateagency.model.response.MessageResponse;
import com.java.web.estateagency.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Tag(
        name = "CRUD REST APIs for User in Estateagency",
        description = "CRUD REST APIs in Estateagency to CREATE, UPDATE, FETCH AND DELETE for User"
)
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/username")
    @Operation(
            summary = "Get User by Username REST API",
            description = "REST API to Get User by Username inside Estateagency"
    )
    public ResponseEntity<User> getuser(@RequestParam("username") String username) {
        User user = userService.getUserByUsername(username).get();
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update")
    @Operation(
            summary = "Update User REST API",
            description = "REST API to Update User inside Estateagency"
    )
    public ResponseEntity<User> updateProfile(@RequestParam("id") String id, @RequestParam("username") String username,
            @RequestParam("fullname") String fullname, @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("email") String email,
            @RequestParam("img") MultipartFile img) {
        UpdateProfileRequest request = new UpdateProfileRequest();
        request.setUsername(username);
        request.setEmail(email);
        request.setAddress(address);
        request.setFullname(fullname);
        request.setPhone(phone);
        request.setId(Long.parseLong(id));
        request.setImg(img);
        User user = userService.updateUser(request);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/agent")
    @Operation(
            summary = "Get All Agent User REST API",
            description = "REST API to Get All Agent User inside Estateagency"
    )
    public ResponseEntity<List<User>> getAgent() {
        List<User> agents = userService.getListAgent();
        return ResponseEntity.ok(agents);
    }

    @GetMapping("/all")
    @Operation(
            summary = "Get All User REST API",
            description = "REST API to Get All User inside Estateagency"
    )
    public ResponseEntity<List<User>> getAll() {
        List<User> all = userService.getAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get User by Id REST API",
            description = "REST API to Get User by Id inside Estateagency"
    )
    public ResponseEntity<User> getProduct(@PathVariable long id) {
        User user = userService.getUsserId(id);

        return ResponseEntity.ok(user);
    }

    @PutMapping("/password")
    @Operation(
            summary = "Change Password User REST API",
            description = "REST API to Change Password User inside Estateagency"
    )
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request){
        log.info(request.toString());
        userService.changePassword(request);
        return ResponseEntity.ok(new MessageResponse("Change Password Success!"));
    }
}
