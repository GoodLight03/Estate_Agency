package com.java.web.estateagency.service;

import com.java.web.estateagency.entity.Order;
import com.java.web.estateagency.model.request.CreateOrderRequest;

public interface OrderService {
    Order save(CreateOrderRequest request);
}
