package com.smart.wccs.dto;

import com.smart.wccs.model.Department;
import com.smart.wccs.model.Position;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PositionDto {
    private String name;


    public Position toPosition() {
        Position position = new Position();
        position.setName(name);
        return position;
    }

    public static PositionDto fromPosition(Position position) {
        PositionDto positionDto = new PositionDto();
        positionDto.setName(position.getName());
        return positionDto;
    }

    public static List<PositionDto> positionDtoList(List<Position> positions) {
        List<PositionDto> positionDtoList = new ArrayList<>();
        positions.forEach(position -> positionDtoList.add(fromPosition(position)));

        return positionDtoList;
    }
}
