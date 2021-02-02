package com.smart.wccs.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "department")
@Data
public class Department extends BaseEntity {

    private String name;

}
