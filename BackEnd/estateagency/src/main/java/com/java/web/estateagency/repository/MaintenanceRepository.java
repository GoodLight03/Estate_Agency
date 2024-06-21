package com.java.web.estateagency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.web.estateagency.entity.Maintenance;
import com.java.web.estateagency.entity.Order;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance,Long>{
      @Query("select p from Maintenance p where p.room.id =?1")
    List<Maintenance> getListByRoon(Long id);

    @Query("select p from Maintenance p where p.room.user.id =?1")
    List<Maintenance> getListAgent(Long id);
}
