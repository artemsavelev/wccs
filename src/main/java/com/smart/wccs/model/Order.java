package com.smart.wccs.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "orders")
@Data
public class Order extends BaseEntity {

    private String extId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    private String customer;
    private String address;
    private Date verificationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verification_name_id")
    private User verificationName;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "department_orders",
//            joinColumns = @JoinColumn(name = "orders_id"),
//            inverseJoinColumns = @JoinColumn(name = "department_id"))
//    private List<Department> departments;
}
