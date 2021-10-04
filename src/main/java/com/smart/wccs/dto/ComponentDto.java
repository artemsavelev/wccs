package com.smart.wccs.dto;

import com.smart.wccs.model.Components;
import lombok.Data;

@Data
public class ComponentDto implements Components {

    private Long id;
    private String name;
    private String dimension;
    private int quantity;
    private double price;
    private String note;

    public ComponentDto(Long id, String name, String dimension, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.dimension = dimension;
        this.quantity = quantity;
        this.price = price;
    }
}
