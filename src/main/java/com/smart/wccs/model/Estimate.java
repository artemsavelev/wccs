package com.smart.wccs.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "estimate")
@Data
public class Estimate extends BaseEntity {

    @JsonView({Views.UserView.class, Views.AdminView.class})
    private String extId;

    @JsonView({Views.UserView.class, Views.AdminView.class})
    private String customer;

    @JsonView({Views.UserView.class, Views.AdminView.class})
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private User author;

    @JsonView({Views.UserView.class, Views.AdminView.class})
    private String workDescription;

    @Transient
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private String simpleText;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "estimate_devices",
            joinColumns = @JoinColumn(name = "estimate_id"),
            inverseJoinColumns = @JoinColumn(name = "devices_id"))
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private List<Device> devices;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "estimate_materials",
            joinColumns = @JoinColumn(name = "estimate_id"),
            inverseJoinColumns = @JoinColumn(name = "materials_id"))
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private List<Material> materials;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "estimate_works",
            joinColumns = @JoinColumn(name = "estimate_id"),
            inverseJoinColumns = @JoinColumn(name = "works_id"))
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private List<Work> works;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private Department department;
}
