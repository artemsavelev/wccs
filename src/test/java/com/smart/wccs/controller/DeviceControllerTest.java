package com.smart.wccs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.wccs.Authentication;
import com.smart.wccs.model.Device;
import com.smart.wccs.service.DeviceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DeviceControllerTest extends Authentication {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listDeviceTest() throws Exception {
        final String token = extractToken(login().andReturn());

        Device device1 = new Device();
        device1.setId(1L);
        device1.setName("Test name1");

        Device device2 = new Device();
        device2.setId(2L);
        device2.setName("Test name2");

        List<Device> devices = new ArrayList<>();
        devices.add(device1);
        devices.add(device2);

        Mockito.when(deviceService.getAllDevice()).thenReturn(devices);
        Assertions.assertNotNull(devices);

        this.mockMvc.perform(get("/api/v1/device/")
                .header("Authorization", "bearer_" + token))
                .andExpect(status().isOk());
        Mockito.verify(deviceService, Mockito.times(1)).getAllDevice();
    }

    @Test
    void getDeviceTest() throws Exception {
        final String token = extractToken(login().andReturn());
        long id = 1L;
        Device device = new Device();
        device.setId(1L);
        device.setName("Test name");

        Mockito.when(deviceService.getById(Mockito.any())).thenReturn(device);
        Assertions.assertNotNull(device);

        this.mockMvc.perform(get("/api/v1/device/{id}", id)
                .header("Authorization", "bearer_" + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Test name"));
        Mockito.verify(deviceService, Mockito.times(2)).getById(id);
    }

    @Test
    void saveDeviceTest() throws Exception {
        final String token = extractToken(login().andReturn());
        Device device = new Device();
        device.setName("Test name1");
        Assertions.assertNotNull(device);

        Mockito.when(deviceService.create(Mockito.any())).thenReturn(device);

        this.mockMvc.perform(post("/api/v1/device/")
                .header("Authorization", "bearer_" + token)
                .content(objectMapper.writeValueAsString(device))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test name1"));
        Mockito.verify(deviceService, Mockito.times(1)).create(device);
    }

}