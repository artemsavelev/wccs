package com.smart.wccs.service.impl;

import com.smart.wccs.model.Department;
import com.smart.wccs.repo.DepartmentRepo;
import com.smart.wccs.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Utils {

    private final UserRepo userRepo;
    private final DepartmentRepo departmentRepo;

    @Autowired
    public Utils(UserRepo userRepo, DepartmentRepo departmentRepo) {
        this.userRepo = userRepo;
        this.departmentRepo = departmentRepo;
    }

    public String getAuthUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    public List<Department> getDepartmentWithUser() {
        Department department = departmentRepo.findDepartmentById(userRepo.findByUsername(getAuthUserName()).getDepartment().getId());
        List<Department> departments = new ArrayList<>();
        departments.add(department);
        return departments;
    }
}
