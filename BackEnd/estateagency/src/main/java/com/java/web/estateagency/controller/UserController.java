package com.java.web.estateagency.controller;

import java.util.List;

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

import com.java.web.estateagency.entity.User;
import com.java.web.estateagency.model.request.UpdateProfileRequest;
import com.java.web.estateagency.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*",maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;
    

    @GetMapping("/")
    @Operation(summary="Lấy ra user bằng username")
    public ResponseEntity<User> getuser(@RequestParam("username") String username){
        User user = userService.getUserByUsername(username).get();
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update")
    @Operation(summary="Cập nhật user")
    public ResponseEntity<User> updateProfile(@RequestBody UpdateProfileRequest request){
        User user = userService.updateUser(request);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/agent")
    @Operation(summary = "All Agent")
    public ResponseEntity<List<User>> getAgent(){
        List<User> agents=userService.getListAgent();
        return ResponseEntity.ok(agents);
    }

    @GetMapping("/all")
    @Operation(summary = "All User")
    public ResponseEntity<List<User>> getAll(){
        List<User> all=userService.getAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    @Operation(summary="Lấy Usser bằng id")
    public ResponseEntity<User> getProduct(@PathVariable long id){
        User user = userService.getUsserId(id);

        return ResponseEntity.ok(user);
    }
}
