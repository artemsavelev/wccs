package com.smart.wccs.service.impl;

import com.smart.wccs.model.Device;
import com.smart.wccs.model.Status;
import com.smart.wccs.repo.DeviceRepo;
import com.smart.wccs.repo.SectionGroupRepo;
import com.smart.wccs.repo.UserRepo;
import com.smart.wccs.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepo deviceRepo;
    private final UserRepo userRepo;
    private final SectionGroupRepo sectionGroupRepo;
    private final Utils utils;

    @Autowired
    public DeviceServiceImpl(DeviceRepo deviceRepo, UserRepo userRepo, SectionGroupRepo sectionGroupRepo, Utils utils) {
        this.deviceRepo = deviceRepo;
        this.userRepo = userRepo;
        this.sectionGroupRepo = sectionGroupRepo;
        this.utils = utils;
    }

    @Override
    public List<Device> getAllDevice() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();

        List<Device> devices = deviceRepo.findAll()
                .stream()
                .filter(o -> o.getDepartments().contains(userRepo.findByUsername(utils.getAuthUserName()).getDepartment()))
                .collect(Collectors.toList());
        log.info("IN getAllDevices - {} devices found", devices.size());
        return devices;
    }

    @Override
    public Device getById(Long id) {
        Device device = deviceRepo.findById(id).orElse(null);
        if (device == null) {
            log.warn("IN findById - no device found by id: {}", id);
            return null;
        }
        log.info("IN findById - device found by id: {}", device);
        return device;
    }

    @Override
    public void create(Device device) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//
//        Department department = departmentRepo.findDepartmentById(userRepo.findByUsername(name).getDepartment().getId());
//        List<Department> devicesDepartments = new ArrayList<>();
//        devicesDepartments.add(department);

        device.setCreatedDate(LocalDateTime.now());
        device.setStatus(Status.ACTIVE);
        device.setDepartments(utils.getDepartmentWithUser());
        device.setGroup(sectionGroupRepo.findSectionGroupById(device.getGroup().getId()));
        Device createdDevice = deviceRepo.save(device);
        log.info("IN create - device: {} successfully added for department: {}", createdDevice, createdDevice.getDepartments());
    }

    @Override
    public List<Device> createAll(List<Device> devices) {
        devices.forEach(device -> device.setCreatedDate(LocalDateTime.now()));
        devices.forEach(device -> device.setStatus(Status.ACTIVE));
        List<Device> createdDevices = deviceRepo.saveAll(devices);
        log.info("IN createAll - device: {} successfully added", createdDevices);
        return createdDevices;
    }

    @Override
    public void delete(Long id) {
        Device device = deviceRepo.findById(id).orElse(null);
        if (device == null) {
            log.warn("IN findById - no device found by id: {}", id);
        } else {
            device.setStatus(Status.DELETED);
        }
        deviceRepo.deleteById(id);
    }
}
