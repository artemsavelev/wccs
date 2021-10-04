package com.smart.wccs.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.smart.wccs.dto.EventType;
import com.smart.wccs.dto.ObjectType;
import com.smart.wccs.dto.WsEventDto;
import com.smart.wccs.model.Department;
import com.smart.wccs.model.Order;
import com.smart.wccs.repo.DepartmentRepo;
import com.smart.wccs.repo.UserRepo;
import com.smart.wccs.service.impl.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.function.BiConsumer;

@Component
public class WsSender {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper mapper;

    @Autowired
    public WsSender(SimpMessagingTemplate messagingTemplate, ObjectMapper mapper) {
        this.messagingTemplate = messagingTemplate;
        this.mapper = mapper;
    }

    public <T> BiConsumer<EventType, T> getSender(ObjectType objectType, Class<?> view) {
        ObjectWriter writer = mapper
                .setConfig(mapper.getSerializationConfig())
                .writerWithView(view);


        return (EventType eventType, T payload) -> {
            String value;

            try {
                value = writer.writeValueAsString(payload);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            Order order = (Order) payload;

            messagingTemplate.convertAndSend(
                    "/topic/messages/" + order.getDepartment().getId(),
                    new WsEventDto(objectType, eventType, value)
            );
        };
    }
}