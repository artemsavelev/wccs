package com.smart.wccs.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "quantity_dev")
@Data
public class QuantityDev {

    @Id
    private Long id;

    private int quantity;


}
