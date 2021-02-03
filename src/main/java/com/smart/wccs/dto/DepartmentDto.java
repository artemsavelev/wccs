package com.smart.wccs.dto;

import com.smart.wccs.model.Department;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DepartmentDto {
    private Long id;
    private String name;

    public Department toDepartment() {
        Department department = new Department();
        department.setId(id);
        department.setName(name);
        return department;
    }

    public static DepartmentDto fromDepartment(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(department.getId());
        departmentDto.setName(department.getName());
        return departmentDto;
    }

    public static List<DepartmentDto> departmentDtoList(List<Department> departments) {
        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        departments.forEach(department -> departmentDtoList.add(fromDepartment(department)));
        return departmentDtoList;
    }
}
