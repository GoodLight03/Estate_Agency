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

import com.java.web.estateagency.entity.Contact;
import com.java.web.estateagency.entity.Request;
import com.java.web.estateagency.model.request.CreateContactRequest;
import com.java.web.estateagency.model.request.CreateRequestRequest;
import com.java.web.estateagency.service.ContactService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Tag(
        name = "CRUD REST APIs for Contact in Estateagency",
        description = "CRUD REST APIs in Estateagency to CREATE, UPDATE, FETCH AND DELETE for Contact"
)
@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class ContactController {
    @Autowired
    private ContactService contactService;

    @GetMapping("/all")
    @Operation(
            summary = "Get All Contact REST API",
            description = "REST API to Get All Contact inside Estateagency"
    )
    public ResponseEntity<?> getMRequest() {
        List<Contact> requests=contactService.findAll();
        return ResponseEntity.ok(requests);
    }

    @PostMapping("/save")
    @Operation(
            summary = "Create Contact REST API",
            description = "REST API to Create Contact inside Estateagency"
    )
    public ResponseEntity<Contact> postMethodName(@RequestBody CreateContactRequest createContactRequest) {
        //TODO: process POST request
        log.info("Hell"+createContactRequest.toString());
        return ResponseEntity.ok(contactService.save(createContactRequest));
    }

    @PostMapping("/update/{id}")
    @Operation(
            summary = "Update Contact REST API",
            description = "REST API to Update Contact and send Email to Customer inside Estateagency"
    )
    public ResponseEntity<Contact> updat(@PathVariable("id") long id, @RequestParam String reply) {
        //TODO: process POST request
        
        return ResponseEntity.ok(contactService.update(reply,id));
    }

    
    
}
