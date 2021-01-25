package com.smart.wccs.controller;

import com.smart.wccs.dto.DeviceDto;
import com.smart.wccs.model.Device;
import com.smart.wccs.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/device/")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public ResponseEntity<List<DeviceDto>> listDevice() {
        List<Device> devices = deviceService.getAllDevice();

        if (devices.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(DeviceDto.deviceDtoList(devices), HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<DeviceDto> getDevice(@PathVariable(name = "id") Long id) {
        Device device = deviceService.getById(id);

        if (device == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        DeviceDto result = DeviceDto.fromDevice(device);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DeviceDto> saveDevice(@RequestBody @Valid Device device) {

        if (device == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        deviceService.create(device);
        return new ResponseEntity<>(DeviceDto.fromDevice(device), HttpStatus.OK);
    }

    @RequestMapping(value = "/addAll", method = RequestMethod.POST)
    public ResponseEntity<List<Device>> saveDeviceAll(@RequestBody List<Device> devices) {
        List<Device> result = deviceService.createAll(devices);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
