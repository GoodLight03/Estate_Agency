package com.java.web.estateagency.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.web.estateagency.entity.Comment;
import com.java.web.estateagency.model.request.CreateCommentCustomerRequest;
import com.java.web.estateagency.repository.CommentRepository;
import com.java.web.estateagency.repository.RoomRepository;
import com.java.web.estateagency.repository.UserRepository;
import com.java.web.estateagency.service.CommentService;
@Service
public class CommentsServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepositorys;

    @Autowired
    private UserRepository userRepositoryss;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Comment saveCommentsCustomer(CreateCommentCustomerRequest commentRequest) {
        Comment comment=new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setDate(new Date());
        comment.setUser(userRepositoryss.findById(commentRequest.getIdCustomer()).get());
        comment.setRoom(roomRepository.findById(commentRequest.getIdRoom()).get());
        return commentRepositorys.save(comment);
    }

    @Override
    public List<Comment> getByRoom(Long id) {
        return commentRepositorys.getByRoom(id);
    }
    
}
