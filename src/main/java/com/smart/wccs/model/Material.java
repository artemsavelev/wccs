package com.smart.wccs.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "material")
@Data
public class Material extends BaseEntity implements Components {

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "materials_departments",
            joinColumns = @JoinColumn(name = "material_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private List<Department> departments;
}
