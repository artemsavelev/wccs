package com.smart.wccs.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private Long id;

    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDate;

    @Enumerated(EnumType.STRING)
    @JsonView(Views.AdminView.class)
    private Status status;

    @JsonView({Views.UserView.class, Views.AdminView.class})
    private String username;

    @JsonView({Views.UserView.class, Views.AdminView.class})
    @Transient
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private Department department;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "position_users",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "position_id"))
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private List<Position> positions;


    public String getName() {
        name = this.getLastName() + " " + this.getFirstName();
        return name;
    }

    @Override
    public String toString() {
        return username;
    }
}
