package com.smart.wccs.service;

import com.smart.wccs.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

    Order getById(Long id);

    void create(Order order);

    void update(Long id);

    void delete(Long id);
}
