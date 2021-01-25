package com.smart.wccs.service;

import com.smart.wccs.model.Device;

import java.util.List;

public interface DeviceService {

    List<Device> getAllDevice();

    Device getById(Long id);

    void create(Device device);

    List<Device> createAll(List<Device> devices);

    void delete(Long id);
}
