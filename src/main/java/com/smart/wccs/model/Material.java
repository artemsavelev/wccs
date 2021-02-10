package com.smart.wccs.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "material")
@Data
public class Material extends BaseEntity {

    @JsonView({Views.UserView.class, Views.AdminView.class})
    private String name;

    @JsonView({Views.UserView.class, Views.AdminView.class})
    private String dimension;

    @Transient
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private int quantity;

    @JsonView({Views.UserView.class, Views.AdminView.class})
    private double price;

    @JsonView({Views.UserView.class, Views.AdminView.class})
    private String note;

    @ManyToMany
    private List<Estimate> estimates;
}
