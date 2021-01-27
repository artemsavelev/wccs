package com.smart.wccs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "estimate")
@Data
public class Estimate extends BaseEntity {

    private String extId;
    private String customer;
    private String address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "estimate_devices",
            joinColumns = @JoinColumn(name = "estimate_id"),
            inverseJoinColumns = @JoinColumn(name = "devices_id"))
    private List<Device> devices;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "estimate_materials",
            joinColumns = @JoinColumn(name = "estimate_id"),
            inverseJoinColumns = @JoinColumn(name = "materials_id"))
    private List<Material> materials;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "estimate_works",
            joinColumns = @JoinColumn(name = "estimate_id"),
            inverseJoinColumns = @JoinColumn(name = "works_id"))
    private List<Work> works;
}
