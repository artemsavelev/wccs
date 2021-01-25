package com.smart.wccs.repo;

import com.smart.wccs.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepo extends JpaRepository<Device, Long> {
}
