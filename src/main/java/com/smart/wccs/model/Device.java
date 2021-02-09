package com.smart.wccs.model;


import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

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

    @OneToOne(optional=false, cascade=CascadeType.ALL)

    private QuantityDev quantityDev;




    @JsonView({Views.UserView.class, Views.AdminView.class})
    private String note;

}
