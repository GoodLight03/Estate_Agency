package com.java.web.estateagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.java.web.estateagency.model.request.CreateOrderRequest;
import com.java.web.estateagency.model.request.CreateRoomRequest;
import com.java.web.estateagency.model.response.MessageResponse;
import com.java.web.estateagency.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    @Operation(summary = "Tạo Phòng")
    public ResponseEntity<?> create(@Valid @RequestBody CreateOrderRequest createOrderRequest) {
        orderService.save(createOrderRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
