package com.smart.wccs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.wccs.Authentication;
import com.smart.wccs.model.Device;
import com.smart.wccs.model.Material;
import com.smart.wccs.service.MaterialService;
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
class MaterialControllerTest extends Authentication {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listMaterialTest() throws Exception {
        final String token = extractToken(login().andReturn());

        Material material1 = new Material();
        material1.setId(1L);
        material1.setName("Test name1");

        Material material2 = new Material();
        material2.setId(2L);
        material2.setName("Test name2");

        List<Material> materials = new ArrayList<>();
        materials.add(material1);
        materials.add(material2);

        Mockito.when(materialService.getAllMaterial()).thenReturn(materials);
        Assertions.assertNotNull(materials);

        this.mockMvc.perform(get("/api/v1/material/")
                .header("Authorization", "bearer_" + token))
                .andExpect(status().isOk());
        Mockito.verify(materialService, Mockito.times(1)).getAllMaterial();
    }

    @Test
    void getMaterialTest() throws Exception {
        final String token = extractToken(login().andReturn());
        long id = 1L;
        Material material = new Material();
        material.setId(id);
        material.setName("Test name");

        Mockito.when(materialService.getById(Mockito.any())).thenReturn(material);
        Assertions.assertNotNull(material);

        this.mockMvc.perform(get("/api/v1/material/{id}", id)
                .header("Authorization", "bearer_" + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Test name"));
        Mockito.verify(materialService, Mockito.times(1)).getById(id);
    }

    @Test
    void saveMaterialTest() throws Exception {
        final String token = extractToken(login().andReturn());
        Material material = new Material();
        material.setName("Test name1");
        Assertions.assertNotNull(material);

        Mockito.when(materialService.create(Mockito.any())).thenReturn(material);

        this.mockMvc.perform(post("/api/v1/material/")
                .header("Authorization", "bearer_" + token)
                .content(objectMapper.writeValueAsString(material))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test name1"));
        Mockito.verify(materialService, Mockito.times(1)).create(material);
    }

}