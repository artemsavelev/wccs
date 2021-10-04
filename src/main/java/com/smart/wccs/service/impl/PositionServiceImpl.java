package com.smart.wccs.service.impl;

import com.smart.wccs.model.Position;
import com.smart.wccs.model.Status;
import com.smart.wccs.repo.PositionRepo;
import com.smart.wccs.service.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Position> positions = positionRepo.findAll()
                .stream()
                .filter(s -> s.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList());
        log.info("IN getAllPosition - {} positions found", positions.size());
        return positions;
    }

    @Override
    public Position getById(Long id) {
        Position position = positionRepo.findById(id).orElse(null);
        if (position == null) {
            log.warn("IN findById - no position found by id: {}", id);
            return null;
        }
        log.info("IN findById - position found by id: {}", position);
        return position;
    }

    @Override
    public void create(Position position) {
        position.setCreatedDate(LocalDateTime.now());
        position.setStatus(Status.ACTIVE);
        Position createdPosition = positionRepo.saveAndFlush(position);
        log.info("IN create - position: {} successfully added", createdPosition);
    }

    @Override
    public void delete(Long id) {
        Position position = positionRepo.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(id, "IN delete - delete: " + id + " not deleted. Position not found "));
        position.setStatus(Status.DELETED);
        positionRepo.saveAndFlush(position);
        log.info("IN delete - position with id: {} successfully deleted", id);
    }
}
