package com.java.web.estateagency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.web.estateagency.entity.Room;
import com.java.web.estateagency.entity.User;
@Repository
public interface RoomRepository extends JpaRepository<Room,Long>  {
    @Query("select p from Room p where p.user.id = ?1 ")
    List<Room> getByAgent(Long id);

    @Query("select p from Room p where p.user.id = ?1 and p.state='Đã thuê'")
    List<Room> getByAgentAngented(Long id);

    @Query("select p from Room p where p.enabled = false and p.state='Chưa thuê'")
    List<Room> getByRoomReportAdmin();

    @Query("select p from Room p where p.user.id = ?1 and p.enabled=true")
    List<Room> getByAgentEnable(Long id);

    @Query("select p from Room p where p.enabled=true ")
    List<Room> getAllEnable();
}
