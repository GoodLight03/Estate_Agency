package com.java.web.estateagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.web.estateagency.entity.Message;

public interface MessageRepository extends JpaRepository<Message,Long>{
    
}
