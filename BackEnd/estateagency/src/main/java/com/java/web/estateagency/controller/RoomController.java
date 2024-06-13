package com.java.web.estateagency.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.java.web.estateagency.entity.Room;
import com.java.web.estateagency.entity.User;
import com.java.web.estateagency.model.request.CreateRoomRequest;
import com.java.web.estateagency.model.request.CreateUserRequest;
import com.java.web.estateagency.model.response.MessageResponse;
import com.java.web.estateagency.service.RoomService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/room")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/create")
    @Operation(summary = "Tạo Phòng")
    public ResponseEntity<?> create(@RequestParam("name") String name,
            @RequestParam("price") String price, @RequestParam("address") String address,
            @RequestParam("describe") String describe, @RequestParam("state") String state,
            @RequestParam("img") MultipartFile img, @RequestParam("idAgent") String idAgent) {

        CreateRoomRequest request = new CreateRoomRequest();
        request.setName(name);
        request.setAddress(address);
        request.setDescribe(describe);
        request.setState(state);
        
        try {
            if (price != null) {
                request.setPrice(Float.parseFloat(price));
            }

            if (idAgent != null) {
                request.setIdAgent(Long.parseLong(idAgent));
            }

        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid price or idAgent."));
        }

        log.info("Hello" + request.toString());
        request.setImg(img);
        roomService.createRoom(request);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/")
    public ResponseEntity<List<Room>> getAll(){
        List<Room> all=roomService.getAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Room> getRoomId(@PathVariable("id")Long id){
        Room all=roomService.detailRoom(id);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<Room>> getAgenRooom(@PathVariable("id")Long id){
        List<Room> all=roomService.getbyAgent(id);
        return ResponseEntity.ok(all);
    }
}
