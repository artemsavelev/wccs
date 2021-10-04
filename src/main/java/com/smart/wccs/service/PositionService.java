package com.smart.wccs.service;

import com.smart.wccs.model.Position;

import java.util.List;

public interface PositionService {

    List<Position> getAllPosition();

    Position getById(Long id);

    void create(Position position);

    void delete(Long id);
}
