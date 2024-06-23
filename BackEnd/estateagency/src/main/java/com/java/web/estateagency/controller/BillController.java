package com.java.web.estateagency.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.web.estateagency.entity.Bill;
import com.java.web.estateagency.entity.Chat;
import com.java.web.estateagency.exception.ChatNotFoundException;
import com.java.web.estateagency.model.request.CreateBillRequest;
import com.java.web.estateagency.model.request.CreateCommentCustomerRequest;
import com.java.web.estateagency.model.response.MessageResponse;
import com.java.web.estateagency.service.BillService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/bill")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class BillController {
    @Autowired
    private BillService billService;

    @GetMapping("/{id}")
    @Operation(summary = "Get By Id Chat")
    public ResponseEntity<List<Bill>> getChatById(@PathVariable Long id) {
        try {
            return new ResponseEntity<List<Bill>>(billService.getByIdContract(id), HttpStatus.OK);
        } catch (Exception e) {
           return new ResponseEntity("Chat Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Tạo Phòng")
    public ResponseEntity<?> create(@Valid @RequestBody CreateBillRequest commentRequest) {
        log.info(commentRequest.toString());
        billService.save(commentRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


    
    @GetMapping("/bill/{id}")
    public ResponseEntity<byte[]> getBill(@PathVariable("id") long id) {

        // return ResponseEntity.ok(contractServices.generateReport(id));
        byte[] pdfContent = billService.generateReport(id);
        // Chuẩn bị HttpHeaders
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "OK" + ".pdf");
        headers.setContentLength(pdfContent.length);

        // Trả về phản hồi HTTP chứa tệp PDF
        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);

    }
}
