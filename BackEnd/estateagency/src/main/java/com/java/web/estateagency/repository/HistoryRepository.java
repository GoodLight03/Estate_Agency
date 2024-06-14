package com.java.web.estateagency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.web.estateagency.entity.History;
import com.java.web.estateagency.entity.Room;
@Repository
public interface HistoryRepository extends JpaRepository<History,Long>{
    @Query("select p from History p where p.user.id = ?1 ")
    List<History> getByCustomer(Long id);
}
