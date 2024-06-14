package com.java.web.estateagency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.web.estateagency.entity.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

    @Query("select p from Order p where p.user.id =?1")
    List<Order> getListByCustomer(Long id);

    @Query("select p from Order p where p.room.id =?1")
    List<Order> getListByRoon(Long id);
}
