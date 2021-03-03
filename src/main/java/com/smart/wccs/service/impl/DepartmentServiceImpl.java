package com.smart.wccs.service.impl;

import com.smart.wccs.model.Department;
import com.smart.wccs.model.Status;
import com.smart.wccs.repo.DepartmentRepo;
import com.smart.wccs.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepo departmentRepo;


    @Autowired
    public DepartmentServiceImpl(DepartmentRepo departmentRepo) {
        this.departmentRepo = departmentRepo;
    }

    @Override
    public List<Department> getAllDepartment() {
        List<Department> departments = departmentRepo.findAll();
        log.info("IN getAllDepartment - {} departments found", departments.size());
        return departments;
    }

    @Override
    public Department getById(Long id) {
        Department department = departmentRepo.findById(id).orElse(null);
        if (department == null) {
            log.warn("IN findById - no department found by id: {}", id);
            return null;
        }
        log.info("IN findById - department found by id: {}", department);
        return null;
    }

    @Override
    public void create(Department department) {
        department.setCreatedDate(LocalDateTime.now());
        department.setStatus(Status.ACTIVE);
        Department createdDepartment = departmentRepo.save(department);
        log.info("IN create - department: {} successfully added", createdDepartment);
    }
}
