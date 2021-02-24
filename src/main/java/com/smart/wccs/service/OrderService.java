package com.smart.wccs.service;

import com.smart.wccs.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    Page<Order> getAllOrders(Pageable pageable);

    Order getById(Long id);

    void create(Order order);

    void update(Long id);

    void delete(Long id);
}
