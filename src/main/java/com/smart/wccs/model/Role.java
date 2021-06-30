package com.smart.wccs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "roles")
@Data
public class Role extends BaseEntity {

    private String roles;

}
