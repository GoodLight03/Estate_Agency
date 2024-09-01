package com.java.web.estateagency.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.web.estateagency.entity.Contract;
import com.java.web.estateagency.entity.History;
import com.java.web.estateagency.entity.Order;
import com.java.web.estateagency.entity.Room;
import com.java.web.estateagency.model.request.CreateOrdersRequest;
import com.java.web.estateagency.repository.ContractsRepository;
import com.java.web.estateagency.repository.HistoryRepository;
import com.java.web.estateagency.repository.OrderRepository;
import com.java.web.estateagency.repository.RoomRepository;
import com.java.web.estateagency.repository.UserRepository;
import com.java.web.estateagency.service.OrderService;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
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
    public Order saveOrders(CreateOrdersRequest request) {
        log.info(request.toString());
        List<Order> contain=orderRepository.findAll().stream()
        .filter(x->x.getRoom().getId()==request.getIdRoom()&&x.getUser().getId()==request.getIdAgent())
        .toList();
        log.info(contain.size()+"");
        if(contain.size()<=0){
            log.info(contain.size()+"mm");
            Order order =new Order();
            order.setRoom(roomRepository.findById(request.getIdRoom()).get());
            order.setUser(userRepository.findById(request.getIdAgent()).get());
            order.setStatus("CHờ phản hồi");
            return orderRepository.save(order);
        }
        return null;
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
            history.setStatus("Đang thuê");
            historyRepository.save(history);

            Contract contract =new Contract();
            contract.setName("Hợp đồng nhà "+ order.getRoom().getName());
            contract.setAcount(order.getUser().getUsername());
            contract.setStart(new Date());
            contract.setUser(order.getRoom().getUser());
            contract.setRoom(order.getRoom());
           contractRepositorys.save(contract);

           Room room= roomRepository.findById(order.getRoom().getId()).get();
           room.setState("Đã thuê");
           room.setEnabled(false);
           roomRepository.save(room);

           List<Order> orderDuyet =orderRepository.findAll()
           .stream().filter(x->x.getRoom().getName().equals(order.getRoom().getName())&&x.getStatus().equals("CHờ phản hồi")&&x.getId()!=order.getId())
           .toList();

           orderDuyet.forEach(od ->{
            od.setStatus("Không duyệt");
            od.setBrowse(true);
           });
           orderRepository.saveAll(orderDuyet);

        }
        order.setStatus(browse);
        order.setBrowse(true);
        return orderRepository.save(order);
    }
    
}
