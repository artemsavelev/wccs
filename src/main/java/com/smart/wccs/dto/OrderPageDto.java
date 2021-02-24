package com.smart.wccs.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.smart.wccs.model.Order;
import com.smart.wccs.model.Views;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonView(Views.UserView.class)
public class OrderPageDto {
    private List<Order> orders;
    private int currentPage;
    private int totalPages;
}
