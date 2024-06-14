package com.java.web.estateagency.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.java.web.estateagency.entity.Order;
import com.java.web.estateagency.model.request.CreateOrderRequest;
import com.java.web.estateagency.model.request.CreateRoomRequest;
import com.java.web.estateagency.model.response.MessageResponse;
import com.java.web.estateagency.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<Order>> getbyCustomer(@PathVariable("id") Long id) {
        List<Order> od = orderService.getorderCustomerss(id);
        return ResponseEntity.ok(od);
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<List<Order>> getbyRoom(@PathVariable("id") Long id) {
        List<Order> od = orderService.getorderRoomss(id);
        return ResponseEntity.ok(od);
    }

    @PatchMapping("/id/{id}/browse/{browse}")
    public ResponseEntity<?> updatebrowse(@PathVariable("id") Long id, @PathVariable("browse") String browse) {
        orderService.updatebrowse(id, browse);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
