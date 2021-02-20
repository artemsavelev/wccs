package com.smart.wccs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "roles")
@Data
public class Role extends BaseEntity {

    private String roles;



}
