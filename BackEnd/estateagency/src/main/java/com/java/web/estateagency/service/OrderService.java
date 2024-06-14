package com.java.web.estateagency.service;

import java.util.List;

import com.java.web.estateagency.entity.Order;
import com.java.web.estateagency.model.request.CreateOrdersRequest;

public interface OrderService {
    Order saveOrders(CreateOrdersRequest request);

    List<Order> getorderCustomerss(Long id);

    List<Order> getorderRoomss(Long id);

    Order updatebrowse(Long id,String browse);
}
