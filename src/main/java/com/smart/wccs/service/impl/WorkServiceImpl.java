package com.smart.wccs.service.impl;

import com.smart.wccs.model.Status;
import com.smart.wccs.model.Work;
import com.smart.wccs.repo.WorkRepo;
import com.smart.wccs.service.WorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class WorkServiceImpl implements WorkService {

    private final WorkRepo workRepo;

    @Autowired
    public WorkServiceImpl(WorkRepo workRepo) {
        this.workRepo = workRepo;
    }

    @Override
    public List<Work> getAllWork() {
        List<Work> works = workRepo.findAll();
        log.info("IN getAllWorks - {} works found", works.size());
        return works;
    }

    @Override
    public Work getById(Long id) {
        Work work = workRepo.findById(id).orElse(null);
        if (work == null) {
            log.warn("IN findById - no work found by id: {}", id);
            return null;
        }
        log.info("IN findById - work found by id: {}", work);
        return work;
    }

    @Override
    public void create(Work work) {
        work.setCreatedDate(LocalDateTime.now());
        work.setStatus(Status.ACTIVE);
        Work createdWork = workRepo.save(work);
        log.info("IN create - work: {} successfully added", createdWork);

    }

    @Override
    public List<Work> createAll(List<Work> works) {
        works.forEach(work -> work.setCreatedDate(LocalDateTime.now()));
        works.forEach(work -> work.setStatus(Status.ACTIVE));
        List<Work> createdWorks = workRepo.saveAll(works);
        log.info("IN createAll - works: {} successfully added", createdWorks);
        return createdWorks;
    }

    @Override
    public void delete(Long id) {
        Work work = workRepo.findById(id).orElse(null);
        if (work == null) {
            log.warn("IN findById - no work found by id: {}", id);
        } else {
            work.setStatus(Status.DELETED);
        }
        workRepo.deleteById(id);

    }
}
