package com.smart.wccs.repo;

import com.smart.wccs.model.SectionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionGroupRepo extends JpaRepository<SectionGroup, Long> {
    SectionGroup findSectionGroupById(Long id);
}
