package com.smart.wccs.service.impl;

import com.smart.wccs.model.Order;
import com.smart.wccs.model.Status;
import com.smart.wccs.model.User;
import com.smart.wccs.repo.OrderRepo;
import com.smart.wccs.repo.UserRepo;
import com.smart.wccs.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final Utils utils;


    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo, UserRepo userRepo, Utils utils) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.utils = utils;
    }

    @Override
    public Page<Order> getAllOrders(Pageable pageable) {

        List<Order> orders =  orderRepo.findAll(pageable)
                .stream()
                .filter(o -> o.getDepartment() == userRepo.findByUsername(utils.getAuthUserName()).getDepartment())
                .filter(o -> o.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList());
        log.info("IN getAllOrder - {} orders found", orders.size());
        return new PageImpl<>(orders, pageable, orders.size());
    }

    @Override
    public Order getById(Long id) {
        Order order = orderRepo.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(id, "IN findById - no order found by id: " + id + ". Order not found "));

        if (order == null) {
            log.warn("IN findById - no order found by id: {}", id);
            return null;
        }
        log.info("IN findById - order found by id: {}", order);
        return order;
    }

    @Override
    public void create(Order order) {

//        String name = order.getAuthor().getUsername();
        User user = userRepo.findByUsername(utils.getAuthUserName());

        order.setCreatedDate(LocalDateTime.now());
        order.setAuthor(user);
        order.setStatus(Status.ACTIVE);
        order.setDepartment(user.getDepartment());

        Order createdOrder = orderRepo.save(order);
        log.info("IN create - order: {} successfully created", createdOrder);
    }

    @Override
    public void update(Long id, Order order) {
        Order orderFromDb = orderRepo.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(id,
                        "IN update - order with id: " + id + " not updated. Order not found "));
        orderFromDb.setExtId(order.getExtId());
        orderFromDb.setUpdatedDate(LocalDateTime.now());
        orderFromDb.setCustomer(order.getCustomer());
        orderFromDb.setAddress(order.getAddress());

        Order createdOrder = orderRepo.save(orderFromDb);
        log.info("IN update - order: {} successfully updated", createdOrder);
    }

    @Override
    public void delete(Long id) {

        Order order = orderRepo.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(id, "IN delete - delete: " + id + " not deleted. Order not found "));

//        orderRepo.deleteById(id);

        order.setStatus(Status.DELETED);
        orderRepo.save(order);
        log.info("IN delete - order with id: {} successfully deleted", id);
    }
}
