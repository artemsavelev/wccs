package com.smart.wccs.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.smart.wccs.model.Department;
import com.smart.wccs.model.Views;
import com.smart.wccs.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/admin/")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(value = "/all-department/")
    @JsonView(Views.AdminView.class)
    public ResponseEntity<List<Department>> listDepartment() {
        List<Department> departments = departmentService.getAllDepartment();

        if (departments == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping(value = "/get-department/{id}")
    @JsonView(Views.UserView.class)
    public ResponseEntity<Department> getDepartment(@PathVariable(name = "id") Long id) {
        Department department = departmentService.getById(id);

        if (department == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @PostMapping(value = "/save-department/")
    @JsonView(Views.AdminView.class)
    public ResponseEntity<?> saveDepartment(@RequestBody Department department) {

        if (department == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        departmentService.create(department);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete-department/{id}")
    @JsonView(Views.AdminView.class)
    public ResponseEntity<?> deleteDepartment(@PathVariable(name = "id") Long id) {
        Department departmentFromDb = departmentService.getById(id);

        if (departmentFromDb == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        departmentService.delete(departmentFromDb.getId());
        return new ResponseEntity<>(departmentFromDb, HttpStatus.OK);
    }
}
