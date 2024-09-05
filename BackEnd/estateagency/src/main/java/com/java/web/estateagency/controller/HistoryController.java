package com.java.web.estateagency.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.web.estateagency.entity.History;
import com.java.web.estateagency.entity.Order;
import com.java.web.estateagency.service.HistoryService;

import io.swagger.v3.oas.annotations.Operation;

@Tag(
        name = "CRUD REST APIs for History in Estateagency",
        description = "CRUD REST APIs in Estateagency to CREATE, UPDATE, FETCH AND DELETE for History"
)
@RestController
@RequestMapping("/api/history")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping("/customer/{id}")
    @Operation(
            summary = "Get History by Customer REST API",
            description = "REST API to Get History by Customer inside Estateagency"
    )
    public ResponseEntity<List<History>> getbyCustomer(@PathVariable("id") Long id) {
        List<History> od = historyService.getByCusumer(id);
        return ResponseEntity.ok(od);
    }
}
