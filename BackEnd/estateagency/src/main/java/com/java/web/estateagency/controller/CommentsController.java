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
import org.springframework.web.bind.annotation.RestController;

import com.java.web.estateagency.entity.Comment;
import com.java.web.estateagency.model.request.CreateCommentCustomerRequest;
import com.java.web.estateagency.model.response.MessageResponse;
import com.java.web.estateagency.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Tag(
        name = "CRUD REST APIs for Comment in Estateagency",
        description = "CRUD REST APIs in Estateagency to CREATE, UPDATE, FETCH AND DELETE for Comment"
)
@RestController
@RequestMapping("/api/comment")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class CommentsController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    @Operation(
            summary = "Create Comment REST API",
            description = "REST API to Create Comment inside Estateagency"
    )
    public ResponseEntity<?> create(@Valid @RequestBody CreateCommentCustomerRequest commentRequest) {
        log.info(commentRequest.toString());
        commentService.saveCommentsCustomer(commentRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/room/{id}")
    @Operation(
            summary = "Get All Comment by Room REST API",
            description = "REST API to Get All Comment by Room on Estateagency"
    )
    public ResponseEntity<List<Comment>> getbyCustomer(@PathVariable("id") Long id) {
        List<Comment> od = commentService.getByRoom(id);
        return ResponseEntity.ok(od);
    }
}
