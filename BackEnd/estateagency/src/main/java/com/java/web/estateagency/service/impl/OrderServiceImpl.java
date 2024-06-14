package com.java.web.estateagency.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.web.estateagency.entity.Contract;
import com.java.web.estateagency.entity.History;
import com.java.web.estateagency.entity.Order;
import com.java.web.estateagency.model.request.CreateOrderRequest;
import com.java.web.estateagency.repository.ContractsRepository;
import com.java.web.estateagency.repository.HistoryRepository;
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

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private ContractsRepository contractRepositorys;

    @Override
    public Order save(CreateOrderRequest request) {
        Order order =new Order();
        order.setRoom(roomRepository.findById(request.getIdRoom()).get());
        order.setUser(userRepository.findById(request.getIdAgent()).get());
        order.setStatus("CHờ phản hồi");
        return orderRepository.save(order);
    }
    @Override
    public List<Order> getorderCustomerss(Long id) {
        List<Order> list= orderRepository.getListByCustomer(id);
        return list;

    }
    @Override
    public List<Order> getorderRoomss(Long id) {
        List<Order> list= orderRepository.getListByRoon(id);
        return list;
    }
    @Override
    public Order updatebrowse(Long id, String browse) {
        Order order=orderRepository.findById(id).get();
        if(browse.equals("Duyệt")){
            History history=new History();
            history.setUser(order.getUser());
            history.setRoom(order.getRoom());
            historyRepository.save(history);

            Contract contract =new Contract();
            contract.setName("Hợp đồng nhà "+ order.getRoom().getName());
            contract.setAcount(order.getUser().getUsername());
            contract.setStart(new Date());
            contract.setUser(order.getRoom().getUser());
            contract.setRoom(order.getRoom());
           contractRepositorys.save(contract);

        }
        order.setStatus(browse);
        order.setBrowse(true);
        return orderRepository.save(order);
    }
    
}
