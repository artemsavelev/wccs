package com.smart.wccs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.smart.wccs.model.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto {
    private Long id;
    private String extId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;
    private String status;
    private UserDto author;
    private String customer;
    private String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date verificationDate;
    private UserDto verificationName;
    private DepartmentDto department;

    public Order toOrder() {
        Order order = new Order();
        User user = new User();
        Department department = new Department();

        order.setId(id);
        order.setExtId(extId);
        order.setCreatedDate(createdDate);
        order.setUpdatedDate(updatedDate);
        order.setStatus(Status.valueOf(status));
        order.setAuthor(UserDto.fromUser(user).toUser());
        order.setCustomer(customer);
        order.setAddress(address);
        order.setVerificationDate(verificationDate);
        order.setVerificationName(UserDto.fromUser(user).toUser());
        order.setDepartment(DepartmentDto.fromDepartment(department).toDepartment());
        return order;
    }

    public static OrderDto fromOrder(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setExtId(order.getExtId());
        orderDto.setCreatedDate(order.getCreatedDate());
        orderDto.setUpdatedDate(order.getUpdatedDate());
        orderDto.setStatus(order.getStatus().name());
        orderDto.setAuthor(UserDto.fromUser(order.getAuthor()));
        orderDto.setCustomer(order.getCustomer());
        orderDto.setAddress(order.getAddress());
        orderDto.setVerificationDate(order.getVerificationDate());
        if (order.getVerificationName() != null) {
            orderDto.setVerificationName(UserDto.fromUser(order.getVerificationName()));
        }
        orderDto.setDepartment(DepartmentDto.fromDepartment(order.getDepartment()));
        return orderDto;
    }

    public static List<OrderDto> orderDtoList(List<Order> orders) {
        List<OrderDto> orderDtoList = new ArrayList<>();
        orders.forEach(order -> orderDtoList.add(fromOrder(order)));
        return orderDtoList;
    }

}
