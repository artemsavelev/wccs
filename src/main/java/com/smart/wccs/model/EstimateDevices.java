package com.smart.wccs.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "estimate_devices")
@Getter
@Setter
public class EstimateDevices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "estimate_id")
    Estimate estimate;

    @ManyToOne
    @JoinColumn(name = "devices_id")
    Device device;

    Integer quantity;

    public EstimateDevices() {
    }

    public EstimateDevices(Estimate estimate, Device device, Integer quantity) {
        this.estimate = estimate;
        this.device = device;
        this.quantity = quantity;
    }
}
