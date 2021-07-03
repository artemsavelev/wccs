package com.smart.wccs.service.impl;

import com.smart.wccs.model.Department;
import com.smart.wccs.model.Device;
import com.smart.wccs.model.Material;
import com.smart.wccs.model.Work;
import com.smart.wccs.repo.DeviceRepo;
import com.smart.wccs.repo.MaterialRepo;
import com.smart.wccs.repo.UserRepo;
import com.smart.wccs.repo.WorkRepo;
import com.smart.wccs.service.ActionToSetService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ActionToSetServiceImpl implements ActionToSetService {

    private final DeviceRepo deviceRepo;
    private final MaterialRepo materialRepo;
    private final WorkRepo workRepo;
    private final Utils utils;
    private final UserRepo userRepo;

    @Autowired
    public ActionToSetServiceImpl(DeviceRepo deviceRepo, MaterialRepo materialRepo, WorkRepo workRepo, Utils utils, UserRepo userRepo) {
        this.deviceRepo = deviceRepo;
        this.materialRepo = materialRepo;
        this.workRepo = workRepo;
        this.utils = utils;
        this.userRepo = userRepo;
    }

    @Override
    public void toSetDevice(Long id) {
        Device deviceFromDb = deviceRepo.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(id,
                        "IN toSetDevice - device with id: " + id + " not updated. Device not found "));
        Department department = userRepo.findByUsername(utils.getAuthUserName()).getDepartment();
        deviceFromDb.getDepartments().add(department);
        deviceRepo.save(deviceFromDb);
    }

    @Override
    public void toSetMaterial(Long id) {
        Material materialFromDb = materialRepo.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(id,
                        "IN toSetMaterial - material with id: " + id + " not updated. Material not found "));
        Department department = userRepo.findByUsername(utils.getAuthUserName()).getDepartment();
        materialFromDb.getDepartments().add(department);
        materialRepo.save(materialFromDb);
    }

    @Override
    public void toSetWork(Long id) {
        Work workFromDb = workRepo.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(id,
                        "IN toSetWork - work with id: " + id + " not updated. Work not found "));
        Department department = userRepo.findByUsername(utils.getAuthUserName()).getDepartment();
        workFromDb.getDepartments().add(department);
        workRepo.save(workFromDb);
    }

    @Override
    public void delSetDevice(Long id) {
        Device deviceFromDb = deviceRepo.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(id,
                        "IN delSetDevice - device with id: " + id + " not deleted. Device not found "));
        Department department = userRepo.findByUsername(utils.getAuthUserName()).getDepartment();
        deviceFromDb.getDepartments().remove(department);
        deviceRepo.save(deviceFromDb);

        // данная логика нужна для соблюдения изолированности между отделами
        deviceRepo.findById(id).filter(dep -> {
            List<Department> depFromDb = dep.getDepartments();
            if (!depFromDb.contains(department)) {
                depFromDb.removeIf(x -> !x.getId().equals(department.getId()));
                return true;
            }
            return false;
        });

    }

    @Override
    public void delSetMaterial(Long id) {
        Material materialFromDb = materialRepo.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(id,
                        "IN delSetMaterial - material with id: " + id + " not deleted. Material not found "));
        Department department = userRepo.findByUsername(utils.getAuthUserName()).getDepartment();
        materialFromDb.getDepartments().remove(department);
        materialRepo.save(materialFromDb);

        // данная логика нужна для соблюдения изолированности между отделами
        materialRepo.findById(id).filter(dep -> {
            List<Department> depFromDb = dep.getDepartments();
            if (!depFromDb.contains(department)) {
                depFromDb.removeIf(x -> !x.getId().equals(department.getId()));
                return true;
            }
            return false;
        });

    }

    @Override
    public void delSetWork(Long id) {
        Work workFromDb = workRepo.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(id,
                        "IN delSetWork - work with id: " + id + " not deleted. Work not found "));
        Department department = userRepo.findByUsername(utils.getAuthUserName()).getDepartment();
        workFromDb.getDepartments().remove(department);
        workRepo.save(workFromDb);

        // данная логика нужна для соблюдения изолированности между отделами
        workRepo.findById(id).filter(dep -> {
            List<Department> depFromDb = dep.getDepartments();
            if (!depFromDb.contains(department)) {
                depFromDb.removeIf(x -> !x.getId().equals(department.getId()));
                return true;
            }
            return false;
        });

    }
}
