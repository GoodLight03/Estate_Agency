package com.java.web.estateagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.web.estateagency.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
    
}
