package com.smart.wccs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.wccs.Authentication;
import com.smart.wccs.model.Work;
import com.smart.wccs.service.WorkService;
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
class WorkControllerTest extends Authentication {

    @Autowired
    private WorkService workService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listWorkTest() throws Exception {
        final String token = extractToken(login().andReturn());

        Work work1 = new Work();
        work1.setId(1L);
        work1.setName("Test name1");

        Work work2 = new Work();
        work2.setId(2L);
        work2.setName("Test name2");

        List<Work> works = new ArrayList<>();
        works.add(work1);
        works.add(work2);

        Mockito.when(workService.getAllWork()).thenReturn(works);
        Assertions.assertNotNull(works);

        this.mockMvc.perform(get("/api/v1/work/")
                .header("Authorization", "bearer_" + token))
                .andExpect(status().isOk());
//                .andExpect(content().json(objectMapper.writeValueAsString(works)));
        Mockito.verify(workService, Mockito.times(1)).getAllWork();
    }

    @Test
    void getWorkTest() throws Exception {
        final String token = extractToken(login().andReturn());
        long id = 1L;
        Work work = new Work();
        work.setId(id);
        work.setName("Test name");

        Mockito.when(workService.getById(Mockito.any())).thenReturn(work);
        Assertions.assertNotNull(work);

        this.mockMvc.perform(get("/api/v1/work/{id}", id)
                .header("Authorization", "bearer_" + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Test name"));
//                .andExpect(jsonPath("$.name", is("Test name")));
        Mockito.verify(workService, Mockito.times(1)).getById(id);
    }

//    @Test
//    void saveWorkTest() throws Exception {
//        final String token = extractToken(login().andReturn());
//        Work work = new Work();
//        work.setName("Test name1");
//        Assertions.assertNotNull(work);
//
//        Mockito.when(workService.create(Mockito.any())).thenReturn(work);
//
//        this.mockMvc.perform(post("/api/v1/work/")
//                .header("Authorization", "bearer_" + token)
//                .content(objectMapper.writeValueAsString(work))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Test name1"));
//        Mockito.verify(workService, Mockito.times(1)).create(work);
//    }

}