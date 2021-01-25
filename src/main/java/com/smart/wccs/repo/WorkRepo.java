package com.smart.wccs.repo;

import com.smart.wccs.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepo extends JpaRepository<Work, Long> {
}
