package com.smart.wccs.repo;

import com.smart.wccs.model.Estimate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstimateRepo extends JpaRepository<Estimate, Long> {
}
