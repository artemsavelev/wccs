package com.smart.wccs.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.smart.wccs.dto.OrderPageDto;
import com.smart.wccs.model.Order;
import com.smart.wccs.model.Views;
import com.smart.wccs.service.OrderService;
import com.smart.wccs.service.filecreator.ExcelDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/order/")
public class OrderController {
    public static final int MESSAGES_PER_PAGE = 30;

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @JsonView(Views.UserView.class)
    public ResponseEntity<OrderPageDto> listOrders(@PageableDefault(size = MESSAGES_PER_PAGE, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {

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

    @MessageMapping("/sendMessage") // websocket endpoint for call saveAll method
    @SendTo("/topic/messages") // websocket endpoint sending messages for subscribes
    @PostMapping
    @JsonView(Views.UserView.class)
    public ResponseEntity<Order> saveOrder(@RequestBody Order order) {

        if (order == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        orderService.create(order);
        return new ResponseEntity<>(order, HttpStatus.OK);

    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @JsonView(Views.UserView.class)
    public ResponseEntity<Order> updateOrder(@PathVariable(name = "id") Long id, @RequestBody Order order) {

        if (order == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        orderService.update(id, order);

        Order orderFromDb = orderService.getById(id);

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
