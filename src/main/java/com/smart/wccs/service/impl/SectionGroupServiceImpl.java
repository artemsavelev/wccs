package com.smart.wccs.service.impl;

import com.smart.wccs.model.SectionGroup;
import com.smart.wccs.model.Status;
import com.smart.wccs.repo.SectionGroupRepo;
import com.smart.wccs.service.SectionGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SectionGroupServiceImpl implements SectionGroupService {

    private final SectionGroupRepo sectionGroupRepo;

    @Autowired
    public SectionGroupServiceImpl(SectionGroupRepo sectionGroupRepo) {
        this.sectionGroupRepo = sectionGroupRepo;
    }


    @Override
    public List<SectionGroup> getAllGroup() {

        List<SectionGroup> sectionGroups = sectionGroupRepo.findAll()
                .stream()
                .filter(o -> o.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList());
        log.info("IN getAllGroup - {} groups found", sectionGroups.size());
        return sectionGroups;
    }

    @Override
    public SectionGroup getById(Long id) {

        SectionGroup sectionGroup = sectionGroupRepo.findById(id).orElse(null);

        if (sectionGroup == null) {
            log.warn("IN findById - SectionGroup not found by id: {}", id);
            return null;
        }
        log.info("IN getById - SectionGroup found by id: {}", sectionGroup);
        return sectionGroup;
    }
}
