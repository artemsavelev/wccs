package com.smart.wccs.repo;

import com.smart.wccs.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepo extends JpaRepository<Position, Long> {
}
