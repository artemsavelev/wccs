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
public class Device extends BaseEntity implements Components {

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "devices_departments",
            joinColumns = @JoinColumn(name = "device_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private List<Department> departments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "section_group_id")
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private SectionGroup group;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private Department author;
}
