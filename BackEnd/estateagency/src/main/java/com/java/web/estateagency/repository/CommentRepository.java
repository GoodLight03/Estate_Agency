package com.java.web.estateagency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.web.estateagency.entity.Comment;
import com.java.web.estateagency.entity.Contract;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select p from Comment p where p.room.id = ?1 ")
    List<Comment> getByRoom(Long id);
}
