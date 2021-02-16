package com.smart.wccs.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {

    @JsonView({Views.UserView.class, Views.AdminView.class})
    private String username;

    @JsonView({Views.UserView.class, Views.AdminView.class})
    private String name;

    @JsonView({Views.UserView.class, Views.AdminView.class})
    private String firstName;

    @JsonView({Views.UserView.class, Views.AdminView.class})
    private String lastName;

    @JsonView({Views.UserView.class, Views.AdminView.class})
    private String email;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonView(Views.AdminView.class)
    private List<Role> roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private Department department;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "position_users",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "position_id"))
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private List<Position> positions;

    @OneToMany
    private List<Order> orders;

}
