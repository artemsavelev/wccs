package com.smart.wccs.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.smart.wccs.model.Views;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@JsonView(Views.IdName.class)
public class WsEventDto {
    private ObjectType objectType;
    private EventType eventType;

    @JsonRawValue
    private String body;
}