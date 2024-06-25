package com.java.web.estateagency.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.web.estateagency.entity.Request;
import com.java.web.estateagency.model.request.CreateRequestRequest;
import com.java.web.estateagency.repository.RequestRepository;
import com.java.web.estateagency.repository.RoomRepository;
import com.java.web.estateagency.repository.UserRepository;
import com.java.web.estateagency.service.RequestService;

@Service
public class RequestServiceImpl implements RequestService{
    @Autowired
    private RequestRepository repository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Request> findAllByIdUser(Long id, String filter) {
        if(filter.equals("User")){
            return repository.getByUser(id);
        }
        return repository.getByRoom(id);
    }

    @Override
    public Request save(CreateRequestRequest createRequestRequest) {
       Request request=new Request();
       request.setDate(new Date());
       request.setDescription(createRequestRequest.getDescription());
       request.setStatus("Chờ xử lý");
       request.setRoom(roomRepository.findById(createRequestRequest.getIdroom()).get());
       request.setUser(userRepository.findById(createRequestRequest.getIduser()).get());
       return repository.save(request);
    }

    @Override
    public Request updateStatus(long id, String status) {
        Request request=repository.findById(id).get();
        request.setStatus(status);
        return repository.save(request);
    }
    
}
