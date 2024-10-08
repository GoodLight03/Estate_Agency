package com.java.web.estateagency.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.java.web.estateagency.entity.Contract;
import com.java.web.estateagency.entity.Order;
import com.java.web.estateagency.service.ContractsService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(
        name = "CRUD REST APIs for Contract in Estateagency",
        description = "CRUD REST APIs in Estateagency to CREATE, UPDATE, FETCH AND DELETE for Contract"
)
@RestController
@RequestMapping("/api/contract")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ContractController {
    @Autowired
    private ContractsService contractServices;

    @GetMapping("/agent/{id}")
    @Operation(
            summary = "Get Contract by Agent REST API",
            description = "REST API to Contract by Agent inside Estateagency"
    )
    public ResponseEntity<List<Contract>> getbyCustomer(@PathVariable("id") Long id) {
        List<Contract> od = contractServices.getbyAgent(id);
        return ResponseEntity.ok(od);
    }

    @GetMapping("/room/{id}")
    @Operation(
            summary = "Get Contract by Room REST API",
            description = "REST API to Contract by Room inside Estateagency"
    )
    public ResponseEntity<List<Contract>> getbyRoom(@PathVariable("id") Long id) {
        List<Contract> od = contractServices.getByRoom(id);
        return ResponseEntity.ok(od);
    }

    @PostMapping("/upFile")
    @Operation(
            summary = "Upload Contract File REST API",
            description = "REST API to Upload Contract File inside Estateagency"
    )
    public ResponseEntity<Contract> upload(@RequestParam("id") Long id, @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(contractServices.upFile(id, file));
    }

    @GetMapping("/getfile/{id}")
    @Operation(
            summary = "Get Contract File REST API",
            description = "REST API to Get Contract File inside Estateagency"
    )
    public ResponseEntity<byte[]> getFIle(@PathVariable Long id) {
        return ResponseEntity.ok(contractServices.getFIle(id));
    }

    

}
