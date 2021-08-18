package com.smart.wccs.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class Order {

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
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private Status status;

    @JsonView({Views.UserView.class, Views.AdminView.class})
    private String extId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private User author;

    @JsonView({Views.UserView.class, Views.AdminView.class})
    private String customer;

    @JsonView({Views.UserView.class, Views.AdminView.class})
    private String address;

    @JsonView({Views.UserView.class, Views.AdminView.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime verificationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verification_name_id")
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private User verificationName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private Department department;

    @JsonView({Views.UserView.class, Views.AdminView.class})
    private Integer versionEstimate;
}
