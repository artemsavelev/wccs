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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminDeviceControllerTest extends Authentication {


    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void listDeviceForAdmin() {
    }

    @Test
    void toSetDevice() {
    }

    @Test
    void delSetDevice() {
    }

    @Test
    void updateDevice() {
    }

//    @Test
//    void saveDeviceTest() throws Exception {
//        final String token = extractToken(login().andReturn());
//        Device device = new Device();
//        device.setName("Test name");
//        device.setPrice(1.2);
//        Assertions.assertNotNull(device);
//
//        Mockito.when(deviceService.create(Mockito.any())).thenReturn(device);
//
//        this.mockMvc.perform(post("/api/v1/admin/device/save/")
//                .header("Authorization", "bearer_" + token)
//                .content(objectMapper.writeValueAsString(device))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Test name"))
//                .andExpect(jsonPath("$.price").value(1.2));
//        Mockito.verify(deviceService, Mockito.times(1)).create(device);
//    }
}