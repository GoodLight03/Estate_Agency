package com.java.web.estateagency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.web.estateagency.entity.Contract;
import com.java.web.estateagency.entity.Room;

@Repository
public interface ContractsRepository extends JpaRepository<Contract,Long>{
    @Query("select p from Contract p where p.user.id = ?1 ")
    List<Contract> getByAgent(Long id);
}
