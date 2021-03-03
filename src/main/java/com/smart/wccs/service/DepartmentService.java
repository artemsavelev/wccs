package com.smart.wccs.service;

import com.smart.wccs.model.Department;

import java.util.List;

public interface DepartmentService {

    List<Department> getAllDepartment();

    Department getById(Long id);

    void create(Department department);
}
