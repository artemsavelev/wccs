package com.smart.wccs.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "estimate_works")
@Getter
@Setter
public class EstimateWorks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "estimate_id")
    Estimate estimate;

    @ManyToOne
    @JoinColumn(name = "works_id")
    Work work;

    Integer quantity;

    public EstimateWorks() {
    }

    public EstimateWorks(Estimate estimate, Work work, Integer quantity) {
        this.estimate = estimate;
        this.work = work;
        this.quantity = quantity;
    }
}
