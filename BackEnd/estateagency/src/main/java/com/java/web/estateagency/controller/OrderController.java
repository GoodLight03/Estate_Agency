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
import com.java.web.estateagency.model.request.CreateOrdersRequest;
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
    public ResponseEntity<?> create(@Valid @RequestBody CreateOrdersRequest createOrderRequest) {
        orderService.saveOrders(createOrderRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/customer/{id}")
    @Operation(summary = "get Order customer")
    public ResponseEntity<List<Order>> getbyCustomer(@PathVariable("id") Long id) {
        List<Order> od = orderService.getorderCustomerss(id);
        return ResponseEntity.ok(od);
    }

    @GetMapping("/room/{id}")
    @Operation(summary = "get Order room")
    public ResponseEntity<List<Order>> getbyRoom(@PathVariable("id") Long id) {
        List<Order> od = orderService.getorderRoomss(id);
        return ResponseEntity.ok(od);
    }

    @PatchMapping("/id/{id}/browse/{browse}")
    @Operation(summary = "get Order browser")
    public ResponseEntity<?> updatebrowse(@PathVariable("id") Long id, @PathVariable("browse") String browse) {
        orderService.updatebrowse(id, browse);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
