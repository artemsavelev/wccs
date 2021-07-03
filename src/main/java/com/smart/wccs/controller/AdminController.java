package com.smart.wccs.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.smart.wccs.model.*;
import com.smart.wccs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminController {

    private final UserService userService;
    private final ActionToSetService actionToSetService;
    private final DeviceService deviceService;
    private final MaterialService materialService;
    private final WorkService workService;

    @Autowired
    public AdminController(UserService userService, ActionToSetService actionToSetService, DeviceService deviceService, MaterialService materialService, WorkService workService) {
        this.userService = userService;
        this.actionToSetService = actionToSetService;
        this.deviceService = deviceService;
        this.materialService = materialService;
        this.workService = workService;
    }

    @GetMapping(value = "users/{id}")
    @JsonView(Views.AdminView.class)
    public ResponseEntity<User> getUser(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "users")
    @JsonView(Views.AdminView.class)
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.getAllUsers();

        if (users == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("registration")
    @JsonView(Views.AdminView.class)
    public ResponseEntity<?> registration(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @RequestMapping(value = "device/view-set", method = RequestMethod.GET)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<List<Device>> listDeviceForAdmin() {
        List<Device> devices = deviceService.getAllDeviceForAdmin();

        if (devices.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @RequestMapping(value = "device/to-set/{id}", method = RequestMethod.PATCH)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<Device> toSetDevice(@PathVariable(name = "id") Long id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        actionToSetService.toSetDevice(id);
        Device deviceFromDb = deviceService.getById(id);
        return new ResponseEntity<>(deviceFromDb, HttpStatus.OK);
    }

    @RequestMapping(value = "device/del-set/{id}", method = RequestMethod.DELETE)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<Device> delSetDevice(@PathVariable(name = "id") Long id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        actionToSetService.delSetDevice(id);
        Device deviceFromDb = deviceService.getById(id);
        return new ResponseEntity<>(deviceFromDb, HttpStatus.OK);
    }

    @RequestMapping(value = "material/view-set", method = RequestMethod.GET)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<List<Material>> listMaterialForAdmin() {
        List<Material> materials = materialService.getAllMaterialForAdmin();

        if (materials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(materials, HttpStatus.OK);
    }

    @RequestMapping(value = "material/to-set/{id}", method = RequestMethod.PATCH)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<Material> toSetMaterial(@PathVariable(name = "id") Long id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        actionToSetService.toSetMaterial(id);
        Material materialFromDb = materialService.getById(id);
        return new ResponseEntity<>(materialFromDb, HttpStatus.OK);
    }

    @RequestMapping(value = "material/del-set/{id}", method = RequestMethod.DELETE)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<Material> delSetMaterial(@PathVariable(name = "id") Long id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        actionToSetService.delSetMaterial(id);
        Material materialFromDb = materialService.getById(id);
        return new ResponseEntity<>(materialFromDb, HttpStatus.OK);
    }

    @RequestMapping(value = "work/view-set", method = RequestMethod.GET)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<List<Work>> listWorkForAdmin() {
        List<Work> works = workService.getAllWorkForAdmin();

        if (works.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(works, HttpStatus.OK);
    }

    @RequestMapping(value = "work/to-set/{id}", method = RequestMethod.PATCH)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<Work> toSetWork(@PathVariable(name = "id") Long id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        actionToSetService.toSetWork(id);
        Work workFromDb = workService.getById(id);
        return new ResponseEntity<>(workFromDb, HttpStatus.OK);
    }

    @RequestMapping(value = "work/del-set/{id}", method = RequestMethod.DELETE)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<Work> delSetWork(@PathVariable(name = "id") Long id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        actionToSetService.delSetWork(id);
        Work workFromDb = workService.getById(id);
        return new ResponseEntity<>(workFromDb, HttpStatus.OK);
    }

}
