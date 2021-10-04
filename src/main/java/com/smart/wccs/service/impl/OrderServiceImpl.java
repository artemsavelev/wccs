package com.smart.wccs.service.impl;

import com.smart.wccs.model.Department;
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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        Department department = userRepo.findByUsername(utils.getAuthUserName()).getDepartment();

        List<Order> orders =  orderRepo.findAllByDepartment(department, pageable)
                .stream()
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
    public Order create(Order order) {
        User user = userRepo.findByUsername(utils.getAuthUserName());
        order.setCreatedDate(LocalDateTime.now());
        order.setAuthor(user);
        order.setStatus(Status.ACTIVE);
        order.setDepartment(user.getDepartment());
        order.setVersionEstimate(Status.ACTIVE);

        Order createdOrder = orderRepo.saveAndFlush(order);
        log.info("IN create - order: {} successfully created", createdOrder);
        return createdOrder;
    }

    @Override
    public Order update(Long id, Order order) {
        Order orderFromDb = orderRepo.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(id,
                        "IN update - order with id: " + id + " not updated. Order not found "));
        orderFromDb.setExtId(order.getExtId());
        orderFromDb.setUpdatedDate(LocalDateTime.now());
        orderFromDb.setCustomer(order.getCustomer());
        orderFromDb.setAddress(order.getAddress());

        Order updatedOrder = orderRepo.saveAndFlush(orderFromDb);
        log.info("IN update - order: {} successfully updated", updatedOrder);
        return updatedOrder;
    }

    @Override
    public void delete(Long id) {
        Order order = orderRepo.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(id, "IN delete - delete: " + id + " not deleted. Order not found "));
//        orderRepo.deleteById(id);
        order.setStatus(Status.DELETED);
        orderRepo.saveAndFlush(order);
        log.info("IN delete - order with id: {} successfully deleted", id);
    }

    @Override
    public List<Order> search(String value) {
        Department department = userRepo.findByUsername(utils.getAuthUserName()).getDepartment();

        long start = System.currentTimeMillis();

        List<Order> orders = orderRepo.findOrdersByDepartment(department, value)
                .stream()
                .filter(o -> o.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList());

        long end = System.currentTimeMillis();

        long total = end - start;

        log.info("IN search - '{}' search value", value);
        log.info("IN search - {} orders found for {} ms", orders.size(), total);
        log.info("IN search - {} orders found", orders);
        return orders;
    }
}
