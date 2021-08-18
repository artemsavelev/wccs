package com.smart.wccs.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "device")
@Data
@NoArgsConstructor
public class Device implements Components {

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

    @ManyToMany(fetch = FetchType.LAZY)
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

    public Device(String name) {
        this.name = name;
    }
}
