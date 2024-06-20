package com.java.web.estateagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.web.estateagency.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long>{
    
}
