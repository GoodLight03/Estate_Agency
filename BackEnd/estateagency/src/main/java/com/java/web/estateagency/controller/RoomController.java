package com.java.web.estateagency.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

@Tag(
        name = "CRUD REST APIs for Room in Estateagency",
        description = "CRUD REST APIs in Estateagency to CREATE, UPDATE, FETCH AND DELETE for Room"
)
@RestController
@RequestMapping("/api/room")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/create")
    @Operation(
            summary = "Create Room REST API",
            description = "REST API to Create Room inside Estateagency"
    )
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

    @PutMapping("/update")
    @Operation(
            summary = "Update Room REST API",
            description = "REST API to Update Room inside Estateagency"
    )
    public ResponseEntity<?> update(@RequestParam("id") String id,@RequestParam("name") String name,
            @RequestParam("price") String price, @RequestParam("address") String address,
            @RequestParam("describe") String describe, @RequestParam("state") String state,
            @RequestParam("img") MultipartFile img, @RequestParam("idAgent") String idAgent) {

        CreateRoomRequest request = new CreateRoomRequest();
        request.setId(Long.parseLong(id));
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
        roomService.updateRoom(request);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Operation(
            summary = "Delete Room REST API",
            description = "REST API to Delete Room inside Estateagency"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")Long id){
        roomService.delete(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/")
    @Operation(
            summary = "Get All Room REST API",
            description = "REST API to Get All Room inside Estateagency"
    )
    public ResponseEntity<List<Room>> getAll(){
        List<Room> all=roomService.getAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/allenable")
    @Operation(
            summary = "Get All Room Enabled REST API",
            description = "REST API to Get All Room Enabled inside Estateagency"
    )
    public ResponseEntity<List<Room>> getAllEnable(){
        List<Room> all=roomService.getbyAllEnable();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/detail/{id}")
    @Operation(
            summary = "Get Detail Room REST API",
            description = "REST API to Get Detail Room by Id inside Estateagency"
    )
    public ResponseEntity<Room> getRoomId(@PathVariable("id")Long id){
        log.info("id:"+id);
        Room all=roomService.detailRoom(id);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/all/{id}")
    @Operation(
            summary = "Get All Room by Agent REST API",
            description = "REST API to Get All Room by Agent Id inside Estateagency"
    )
    public ResponseEntity<List<Room>> getAgenRooom(@PathVariable("id")Long id){
        List<Room> all=roomService.getbyAgent(id);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/allenableAgent/{id}")
    @Operation(
            summary = "Get All Room Enabled by Agent REST API",
            description = "REST API to Get All Room Enabled by Agent Id inside Estateagency"
    )
    public ResponseEntity<List<Room>> getAgenRooomEnable(@PathVariable("id")Long id){
        List<Room> all=roomService.getbyAgentEnable(id);
        return ResponseEntity.ok(all);
    }

    @PostMapping("/enable/{id}")
    @Operation(
            summary = "Enable Room REST API",
            description = "REST API to Enable Room inside Estateagency"
    )
    public ResponseEntity<Room> enable(@PathVariable("id")Long id, @RequestParam Boolean check){
        return ResponseEntity.ok(roomService.enable(id,check));
    }

}
