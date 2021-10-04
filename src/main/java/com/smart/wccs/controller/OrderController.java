package com.smart.wccs.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.smart.wccs.dto.EventType;
import com.smart.wccs.dto.ObjectType;
import com.smart.wccs.dto.OrderPageDto;
import com.smart.wccs.model.Order;
import com.smart.wccs.model.Views;
import com.smart.wccs.service.OrderService;
import com.smart.wccs.util.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping(value = "/api/v1/order/")
public class OrderController {

    public static final int ORDERS_PER_PAGE = 30;
    private final OrderService orderService;
    private final BiConsumer<EventType, Order> wsSender;

    @Autowired
    public OrderController(OrderService orderService, WsSender wsSender) {
        this.orderService = orderService;
        this.wsSender = wsSender.getSender(ObjectType.ORDER, Views.UserView.class);
    }

    @GetMapping
    @JsonView(Views.UserView.class)
    public ResponseEntity<OrderPageDto> listOrders(@PageableDefault(size = ORDERS_PER_PAGE, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Order> orders = orderService.getAllOrders(pageable);
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new OrderPageDto(orders.getContent(), pageable.getPageNumber(), orders.getTotalPages()), HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    @JsonView(Views.UserView.class)
    public ResponseEntity<Order> getOrder(@PathVariable(name = "id") Long id) {
        Order order = orderService.getById(id);

        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping
    @JsonView(Views.UserView.class)
    public ResponseEntity<Order> saveOrder(@RequestBody Order order) {

        if (order == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Order orderFromDb = orderService.create(order);
        // ws
        wsSender.accept(EventType.CREATE, orderFromDb);
        return new ResponseEntity<>(orderFromDb, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @JsonView(Views.UserView.class)
    public ResponseEntity<Order> updateOrder(@PathVariable(name = "id") Long id, @RequestBody Order order) {

        if (order == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Order orderFromDb = orderService.update(id, order);;
        // ws
        wsSender.accept(EventType.UPDATE, orderFromDb);
        return new ResponseEntity<>(orderFromDb, HttpStatus.OK);

    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @JsonView(Views.UserView.class)
    public ResponseEntity<?> deleteOrder(@PathVariable(name = "id") Long id) {
        Order orderFromDb = orderService.getById(id);

        if (orderFromDb == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        orderService.delete(orderFromDb.getId());
        // ws
        wsSender.accept(EventType.REMOVE, orderFromDb);
        return new ResponseEntity<>(orderFromDb, HttpStatus.OK);
    }

    @RequestMapping(value = "search/", method = RequestMethod.GET)
    @JsonView({Views.UserView.class})
    public ResponseEntity<List<Order>> searchOrders(@RequestParam("q") String value) {

        List<Order> orders = orderService.search(value.toLowerCase());

        if (value.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


}
