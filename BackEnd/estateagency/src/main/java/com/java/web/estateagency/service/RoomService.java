package com.java.web.estateagency.service;

import java.util.List;

import com.java.web.estateagency.entity.Room;
import com.java.web.estateagency.model.request.CreateRoomRequest;

public interface RoomService {
    Room createRoom(CreateRoomRequest createRoomRequest);

    List<Room> getAll();

    List<Room> getbyAgent(Long idAgent);

    Room detailRoom(Long id);
}
