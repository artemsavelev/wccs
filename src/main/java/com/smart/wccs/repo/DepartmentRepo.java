package com.smart.wccs.repo;

import com.smart.wccs.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department, Long> {
    Department findDepartmentById(Long id);
}
