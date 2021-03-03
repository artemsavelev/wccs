package com.smart.wccs.service.impl;

import com.smart.wccs.model.Position;
import com.smart.wccs.model.Status;
import com.smart.wccs.repo.PositionRepo;
import com.smart.wccs.service.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class PositionServiceImpl implements PositionService {

    private final PositionRepo positionRepo;

    @Autowired
    public PositionServiceImpl(PositionRepo positionRepo) {
        this.positionRepo = positionRepo;
    }

    @Override
    public List<Position> getAllPosition() {
        List<Position> positions = positionRepo.findAll();
        log.info("IN getAllPosition - {} positions found", positions.size());
        return null;
    }

    @Override
    public Position getById(Long id) {
        Position position = positionRepo.findById(id).orElse(null);
        if (position == null) {
            log.warn("IN findById - no position found by id: {}", id);
            return null;
        }
        log.info("IN findById - position found by id: {}", position);
        return null;
    }

    @Override
    public void create(Position position) {
        position.setCreatedDate(LocalDateTime.now());
        position.setStatus(Status.ACTIVE);
        Position createdPosition = positionRepo.save(position);
        log.info("IN create - position: {} successfully added", createdPosition);
    }
}
