package com.java.web.estateagency.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.web.estateagency.entity.Order;
import com.java.web.estateagency.model.request.CreateOrderRequest;
import com.java.web.estateagency.repository.OrderRepository;
import com.java.web.estateagency.repository.RoomRepository;
import com.java.web.estateagency.repository.UserRepository;
import com.java.web.estateagency.service.OrderService;


@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;
    @Override
    public Order save(CreateOrderRequest request) {
        Order order =new Order();
        order.setRoom(roomRepository.findById(request.getIdRoom()).get());
        order.setUser(userRepository.findById(request.getIdAgent()).get());
        order.setStatus("CHờ phản hồi");
        return orderRepository.save(order);
    }
    
}
