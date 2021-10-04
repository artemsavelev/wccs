package com.smart.wccs.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "estimate_materials")
@Getter
@Setter
public class EstimateMaterials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "estimate_id")
    Estimate estimate;

    @ManyToOne
    @JoinColumn(name = "materials_id")
    Material material;

    Integer quantity;

    public EstimateMaterials() {
    }

    public EstimateMaterials(Estimate estimate, Material material, Integer quantity) {
        this.estimate = estimate;
        this.material = material;
        this.quantity = quantity;
    }
}
