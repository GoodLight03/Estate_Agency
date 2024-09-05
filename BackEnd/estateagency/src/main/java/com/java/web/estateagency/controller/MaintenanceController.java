package com.java.web.estateagency.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.java.web.estateagency.entity.Contract;
import com.java.web.estateagency.entity.Maintenance;
import com.java.web.estateagency.entity.Request;
import com.java.web.estateagency.model.request.CreateMaintenanceRequest;
import com.java.web.estateagency.model.request.CreateRequestRequest;
import com.java.web.estateagency.service.MaintenanceService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Tag(
        name = "CRUD REST APIs for Maintenance in Estateagency",
        description = "CRUD REST APIs in Estateagency to CREATE, UPDATE, FETCH AND DELETE for Maintenance"
)
@RestController
@RequestMapping("/api/maintenance")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

     @GetMapping("/all/{id}")
     @Operation(
             summary = "Get Maintenance by Agent REST API",
             description = "REST API to Get Maintenance by Agent inside Estateagency"
     )
    public ResponseEntity<?> getMRequest(@PathVariable("id") long id) {
        List<Maintenance> requests=maintenanceService.getbtIdAgent(id);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/allbyroom/{id}")
    @Operation(
            summary = "Get Maintenance by Room REST API",
            description = "REST API to Get Maintenance by Room inside Estateagency"
    )
    public ResponseEntity<?> getMRequestRoom(@PathVariable("id") long id) {
        List<Maintenance> requests=maintenanceService.getbtIdRoom(id);
        return ResponseEntity.ok(requests);
    }

    @PostMapping("/save")
    @Operation(
            summary = "Save Maintenance REST API",
            description = "REST API to Save Maintenance inside Estateagency"
    )
    public ResponseEntity<Maintenance> postMethodName(@RequestBody CreateMaintenanceRequest createMaintenanceRequest) {
        //TODO: process POST request
        log.info("Hell"+createMaintenanceRequest.toString());
        return ResponseEntity.ok(maintenanceService.save(createMaintenanceRequest));
    }

    @GetMapping("/getall")
    @Operation(summary = "All Get Maintaince")
    public ResponseEntity<?> all() {
        List<Maintenance> requests=maintenanceService.getAll();
        return ResponseEntity.ok(requests);
    }

    @PostMapping("/upFile")
    @Operation(summary = "Up file Maintaince")
    public ResponseEntity<Maintenance> upload(@RequestParam("id") Long id, @RequestParam("file") MultipartFile file) {
        
        return ResponseEntity.ok(maintenanceService.upFile(id, file));
    }

    @GetMapping("/getfile/{id}")
    @Operation(summary = "Get file Maintaince")
    public ResponseEntity<byte[]> getFIle(@PathVariable Long id) {
        return ResponseEntity.ok(maintenanceService.getFIle(id));
    }

}
