package com.java.web.estateagency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.web.estateagency.entity.Request;
import com.java.web.estateagency.entity.Room;

@Repository
public interface RequestRepository extends JpaRepository<Request,Long>{
    @Query("select p from Request p where p.user.id = ?1 ")
    List<Request> getByUser(Long id);

    @Query("select p from Request p where p.room.id = ?1 ")
    List<Request> getByRoom(Long id);

    
}
