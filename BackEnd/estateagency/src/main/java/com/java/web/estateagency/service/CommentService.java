package com.java.web.estateagency.service;

import java.util.List;

import com.java.web.estateagency.entity.Comment;
import com.java.web.estateagency.model.request.CreateCommentCustomerRequest;

public interface CommentService {
    Comment saveCommentsCustomer(CreateCommentCustomerRequest commentRequest);

    List<Comment> getByRoom(Long id);
}
