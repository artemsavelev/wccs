package com.smart.wccs.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "estimate")
@Data
public class Estimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private Long id;

    @CreatedDate
    @JsonView({Views.UserView.class, Views.AdminView.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @JsonView({Views.UserView.class, Views.AdminView.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDate;

    @Enumerated(EnumType.STRING)
    @JsonView(Views.AdminView.class)
    private Status status;

    @Transient
    private String key;

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
