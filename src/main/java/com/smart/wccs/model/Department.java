package com.smart.wccs.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "department")
@Data
public class Department extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "departments")
    private List<User> users;

}
