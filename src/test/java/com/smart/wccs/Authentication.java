package com.smart.wccs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.smart.wccs.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class Authentication {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    protected String extractToken(MvcResult result) throws UnsupportedEncodingException {
        return JsonPath.read(result.getResponse().getContentAsString(), "$.token");
    }

    protected ResultActions login() throws Exception {
        final AuthenticationRequest auth = new AuthenticationRequest();
        auth.setUsername("artem");
        auth.setPassword("Artem1980");
        return mockMvc.perform(post("/api/v1/auth/login")
                .content(json(auth))
                .contentType(MediaType.APPLICATION_JSON));
    }

    protected String json(Object o) throws IOException {
        return mapper.writeValueAsString(o);
    }
}
