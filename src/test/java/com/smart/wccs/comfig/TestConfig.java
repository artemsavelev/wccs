package com.smart.wccs.comfig;

import com.smart.wccs.service.DeviceService;
import com.smart.wccs.service.MaterialService;
import com.smart.wccs.service.WorkService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {

    @Bean
    public DeviceService deviceService() {
        return mock(DeviceService.class);
    }

    @Bean
    public MaterialService materialService() {
        return mock(MaterialService.class);
    }

    @Bean
    public WorkService workService() {
        return mock(WorkService.class);
    }

}
