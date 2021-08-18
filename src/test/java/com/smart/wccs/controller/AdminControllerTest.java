package com.smart.wccs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.wccs.Authentication;
import com.smart.wccs.model.Device;
import com.smart.wccs.model.Material;
import com.smart.wccs.model.Work;
import com.smart.wccs.service.DeviceService;
import com.smart.wccs.service.MaterialService;
import com.smart.wccs.service.WorkService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminControllerTest extends Authentication {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private WorkService workService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUserTest() {
    }

    @Test
    void listUsersTest() {
    }

    @Test
    void registrationTest() {
    }

    @Test
    void listDeviceForAdminTest() {
    }

    @Test
    void toSetDeviceTest() {
    }

    @Test
    void delSetDeviceTest() {
    }

    @Test
    void listMaterialForAdminTest() {
    }

    @Test
    void toSetMaterialTest() {
    }

    @Test
    void delSetMaterialTest() {
    }

    @Test
    void listWorkForAdminTest() {
    }

    @Test
    void toSetWorkTest() {
    }

    @Test
    void delSetWorkTest() {
    }

    @Test
    void updateDeviceTest() throws Exception {
        final String token = extractToken(login().andReturn());
        long id = 1L;
        Device device = new Device("Test 123");
        Assertions.assertNotNull(device);

        Mockito.when(deviceService.update(Mockito.any(), Mockito.any())).thenReturn(device);

        this.mockMvc.perform(put("/api/v1/admin/device/update/{id}", id)
                .header("Authorization", "bearer_" + token)
                .content(objectMapper.writeValueAsString(new Device("Test 123")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(deviceService, Mockito.times(1)).update(id, device);
    }

    @Test
    void updateMaterialTest() throws Exception {
        final String token = extractToken(login().andReturn());
        long id = 1L;
        Material material = new Material("Test 123");
        Assertions.assertNotNull(material);

        Mockito.when(materialService.update(Mockito.any(), Mockito.any())).thenReturn(material);

        this.mockMvc.perform(put("/api/v1/admin/material/update/{id}", id)
                .header("Authorization", "bearer_" + token)
                .content(objectMapper.writeValueAsString(new Device("Test 123")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(materialService, Mockito.times(1)).update(id, material);
    }

    @Test
    void updateWorkTest() throws Exception {
        final String token = extractToken(login().andReturn());
        long id = 1L;
        Work work = new Work("Test 123");
        Assertions.assertNotNull(work);

        Mockito.when(workService.update(Mockito.any(), Mockito.any())).thenReturn(work);

        this.mockMvc.perform(put("/api/v1/admin/work/update/{id}", id)
                .header("Authorization", "bearer_" + token)
                .content(objectMapper.writeValueAsString(new Work("Test 123")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(workService, Mockito.times(1)).update(id, work);
    }
}