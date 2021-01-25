package com.smart.wccs.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "device")
@Data
public class Device extends BaseEntity {

    private String name;

    private String dimension;

    private double price;

    private String note;

}
