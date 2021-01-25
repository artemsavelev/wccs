package com.smart.wccs.repo;

import com.smart.wccs.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepo extends JpaRepository<Material, Long> {
}
