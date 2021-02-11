package com.smart.wccs.model;


import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "device")
@Data
public class Device extends BaseEntity {

    @JsonView({Views.UserView.class, Views.AdminView.class})
    private String name;

    @JsonView({Views.UserView.class, Views.AdminView.class})
    private String dimension;

    @JsonView({Views.UserView.class, Views.AdminView.class})
    private double price;


    @Transient
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private int quantity;


    @JsonView({Views.UserView.class, Views.AdminView.class})
    private String note;

}
