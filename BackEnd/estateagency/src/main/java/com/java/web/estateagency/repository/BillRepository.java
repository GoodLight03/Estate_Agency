package com.java.web.estateagency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.web.estateagency.entity.Bill;
import com.java.web.estateagency.entity.History;

@Repository
public interface BillRepository extends JpaRepository<Bill,Long>{
    @Query("select p from Bill p where p.contract.id = ?1 ")
    List<Bill> getByContractID(Long id);
}
