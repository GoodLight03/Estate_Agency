package com.java.web.estateagency.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.web.estateagency.entity.Request;
import com.java.web.estateagency.model.request.CreateRequestRequest;
import com.java.web.estateagency.service.RequestService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/request")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class RequestController {
    @Autowired
    private RequestService requestService;

    @GetMapping("/all/{id}")
     @Operation(summary = "get Request id")
    public ResponseEntity<?> getMRequest(@PathVariable("id") long id, @RequestParam("filter") String filter) {
        List<Request> requests=requestService.findAllByIdUser(id, filter);
        return ResponseEntity.ok(requests);
    }

    @PostMapping("/save")
    @Operation(summary = "get Request save")
    public ResponseEntity<Request> postMethodName(@RequestBody CreateRequestRequest createRequestRequest) {
        //TODO: process POST request
        log.info("Hell"+createRequestRequest.toString());
        return ResponseEntity.ok(requestService.save(createRequestRequest));
    }

    @PatchMapping("/status/id/{id}/status/{status}")
    public ResponseEntity<Request> putMethodName(@PathVariable("id") long id, @PathVariable("status") String status) {
        //TODO: process PUT request
        log.info(id+status);
        
        return ResponseEntity.ok(requestService.updateStatus(id, status));
    }
    
    
}
