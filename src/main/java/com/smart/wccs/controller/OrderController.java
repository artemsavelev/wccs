package com.smart.wccs.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.smart.wccs.dto.OrderPageDto;
import com.smart.wccs.model.Order;
import com.smart.wccs.model.Views;
import com.smart.wccs.service.OrderService;
import com.smart.wccs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
}
