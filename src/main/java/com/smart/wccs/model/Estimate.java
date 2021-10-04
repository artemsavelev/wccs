package com.smart.wccs.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "estimate")
@Getter
@Setter
public class Estimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({Views.IdName.class, Views.UserView.class, Views.AdminView.class})
    private Long id;

    @CreatedDate
    @JsonView({Views.IdName.class, Views.UserView.class, Views.AdminView.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @JsonView({Views.IdName.class, Views.UserView.class, Views.AdminView.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDate;

    @Enumerated(EnumType.STRING)
    @JsonView(Views.AdminView.class)
    private Status status;

    @Transient
    private String key;

    @JsonView({Views.IdName.class, Views.UserView.class, Views.AdminView.class})
    private String extId;

    @JsonView({Views.IdName.class, Views.UserView.class, Views.AdminView.class})
    private String customer;

    @JsonView({Views.IdName.class, Views.UserView.class, Views.AdminView.class})
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private User author;

    @JsonView({Views.IdName.class, Views.UserView.class, Views.AdminView.class})
    private String workDescription;

    @JsonView({Views.IdName.class, Views.UserView.class, Views.AdminView.class})
    private String simpleText;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "estimate_devices",
//            joinColumns = @JoinColumn(name = "estimate_id"),
//            inverseJoinColumns = @JoinColumn(name = "devices_id"))

    @Transient
    @JsonView({Views.IdName.class, Views.UserView.class, Views.AdminView.class})
    private List<Device> devices = new ArrayList<>();

    @OneToMany(mappedBy = "estimate", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    List<EstimateDevices> estimateDevices = new ArrayList<>();

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "estimate_materials",
//            joinColumns = @JoinColumn(name = "estimate_id"),
//            inverseJoinColumns = @JoinColumn(name = "materials_id"))

    @Transient
    @JsonView({Views.IdName.class, Views.UserView.class, Views.AdminView.class})
    private List<Material> materials = new ArrayList<>();

    @OneToMany(mappedBy = "estimate", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    List<EstimateMaterials> estimateMaterials = new ArrayList<>();

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "estimate_works",
//            joinColumns = @JoinColumn(name = "estimate_id"),
//            inverseJoinColumns = @JoinColumn(name = "works_id"))

    @Transient
    @JsonView({Views.IdName.class, Views.UserView.class, Views.AdminView.class})
    private List<Work> works = new ArrayList<>();

    @OneToMany(mappedBy = "estimate", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    List<EstimateWorks> estimateWorks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonView({Views.UserView.class, Views.AdminView.class})
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonView({Views.IdName.class, Views.UserView.class, Views.AdminView.class})
    private Order order;

    @JsonView({Views.IdName.class, Views.UserView.class, Views.AdminView.class})
    private Double amount;

    @Override
    public String toString() {
        return "Estimate{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", status=" + status +
                ", key='" + key + '\'' +
                ", extId='" + extId + '\'' +
                ", customer='" + customer + '\'' +
                ", address='" + address + '\'' +
                ", author=" + author +
                ", workDescription='" + workDescription + '\'' +
                ", simpleText='" + simpleText + '\'' +
                ", devices=" + devices +
                ", materials=" + materials +
                ", works=" + works +
                ", department=" + department +
                ", order=" + order +
                ", amount=" + amount +
                '}';
    }
}
