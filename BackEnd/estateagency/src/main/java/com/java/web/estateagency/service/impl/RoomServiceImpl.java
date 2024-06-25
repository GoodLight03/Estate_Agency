package com.java.web.estateagency.service.impl;

import java.util.Base64;
import java.util.List;
import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.web.estateagency.entity.Room;
import com.java.web.estateagency.entity.User;
import com.java.web.estateagency.exception.NotFoundException;
import com.java.web.estateagency.model.request.CreateRoomRequest;
import com.java.web.estateagency.repository.RoomRepository;
import com.java.web.estateagency.repository.UserRepository;
import com.java.web.estateagency.service.RoomService;
import com.java.web.estateagency.utils.ImageUpload;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ImageUpload imageUpload;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Room createRoom(CreateRoomRequest createRoomRequest) {
        Room room = new Room();
        room.setName(createRoomRequest.getName());
        room.setPrice(createRoomRequest.getPrice());
        room.setAddress(createRoomRequest.getAddress());
        room.setDescription(createRoomRequest.getDescribe());
        room.setState("Chưa thuê");
        User user = userRepository.findById(createRoomRequest.getIdAgent()).orElseThrow(()-> new NotFoundException("Not Found Category With Id: " + createRoomRequest.getIdAgent()));
        room.setUser(user);
        try {
            if (createRoomRequest.getImg() == null) {
                room.setImg(null);
            } else {
                imageUpload.uploadFile(createRoomRequest.getImg());
                room.setImg(Base64.getEncoder().encodeToString(createRoomRequest.getImg().getBytes()));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return roomRepository.save(room);
    }

    @Override
    public List<Room> getAll() {
       List<Room> rooms=roomRepository.findAll();
       return rooms;
    }

    @Override
    public List<Room> getbyAgent(Long idAgent) {
      return roomRepository.getByAgent(idAgent);
      
    }

    @Override
    public Room detailRoom(Long id) {
       return roomRepository.findById(id).get();
    }

    @Override
    public Room enable(Long id,Boolean chBoolean) {
       Room room=roomRepository.findById(id).get();
       room.setEnabled(chBoolean);
       return roomRepository.save(room);
    }

    @Override
    public List<Room> getbyAgentEnable(Long idAgent) {
       return roomRepository.getByAgentEnable(idAgent);
    }

    @Override
    public List<Room> getbyAllEnable() {
        return roomRepository.getAllEnable();
    }

    @Override
    public Room updateRoom(CreateRoomRequest createRoomRequest) {
        Room room = roomRepository.findById(createRoomRequest.getId()).get();
        room.setName(createRoomRequest.getName());
        room.setPrice(createRoomRequest.getPrice());
        room.setAddress(createRoomRequest.getAddress());
        room.setDescription(createRoomRequest.getDescribe());
        room.setState("Chưa thuê");
        User user = userRepository.findById(createRoomRequest.getIdAgent()).orElseThrow(()-> new NotFoundException("Not Found Category With Id: " + createRoomRequest.getIdAgent()));
        room.setUser(user);
        try {
            if (createRoomRequest.getImg() == null) {
                room.setImg(Base64.getEncoder().encodeToString(room.getImg().getBytes()));
            } else {
                imageUpload.uploadFile(createRoomRequest.getImg());
                room.setImg(Base64.getEncoder().encodeToString(createRoomRequest.getImg().getBytes()));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return roomRepository.save(room);
    }

    @Override
    public void delete(Long id) {
        Room room = roomRepository.findById(id).get();
         roomRepository.delete(room);
    }

}
