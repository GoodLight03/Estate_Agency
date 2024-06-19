package com.java.web.estateagency.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/contract")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ContractController {
    @Autowired
    private ContractsService contractServices;

    @GetMapping("/agent/{id}")
    public ResponseEntity<List<Contract>> getbyCustomer(@PathVariable("id") Long id) {
        List<Contract> od = contractServices.getbyAgent(id);
        return ResponseEntity.ok(od);
    }

    @PostMapping("/upFile")
    public ResponseEntity<Contract> upload(@RequestParam("id") Long id, @RequestParam("file") MultipartFile file) {
        
        return ResponseEntity.ok(contractServices.upFile(id, file));
    }

    @GetMapping("/getfile/{id}")
    public ResponseEntity<byte[]> getFIle(@PathVariable Long id) {
        return ResponseEntity.ok(contractServices.getFIle(id));
    }
    
    
}
