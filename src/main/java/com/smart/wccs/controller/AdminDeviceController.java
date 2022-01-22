package com.smart.wccs.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.smart.wccs.exceptions.BadRequestException;
import com.smart.wccs.model.Device;
import com.smart.wccs.model.Views;
import com.smart.wccs.service.ActionToSetService;
import com.smart.wccs.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminDeviceController {

    private final ActionToSetService actionToSetService;
    private final DeviceService deviceService;

    public AdminDeviceController(ActionToSetService actionToSetService, DeviceService deviceService) {
        this.actionToSetService = actionToSetService;
        this.deviceService = deviceService;
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

    @RequestMapping(value = "device/update/{id}", method = RequestMethod.PUT)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<?> updateDevice(@PathVariable(name = "id") Long id, @RequestBody Device device) {

        if (device == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (device.getPrice() < 0) {
            throw new BadRequestException("Значение цены не может быть отрицательным");
        }

        deviceService.update(id, device);
        Device deviceFromDb = deviceService.getById(id);
        return new ResponseEntity<>(deviceFromDb, HttpStatus.OK);
    }

    @RequestMapping(value = "device/save/", method = RequestMethod.POST)
    @JsonView(Views.AdminView.class)
    public ResponseEntity<?> saveDevice(@RequestBody Device device) {

        if (device == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (device.getPrice() < 0) {
            throw new BadRequestException("Значение цены не может быть отрицательным");
        }

        deviceService.create(device);
        return new ResponseEntity<>(device, HttpStatus.OK);
    }
}
