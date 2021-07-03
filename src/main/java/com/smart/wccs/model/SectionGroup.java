package com.smart.wccs.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "section_group")
@Data
public class SectionGroup extends BaseEntity {


    @JsonView({Views.UserView.class, Views.AdminView.class})
    private String name;
}