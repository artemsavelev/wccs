package com.smart.wccs.service.impl;

import com.smart.wccs.model.Status;
import com.smart.wccs.model.Work;
import com.smart.wccs.repo.SectionGroupRepo;
import com.smart.wccs.repo.UserRepo;
import com.smart.wccs.repo.WorkRepo;
import com.smart.wccs.service.WorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WorkServiceImpl implements WorkService {

    private final WorkRepo workRepo;
    private final UserRepo userRepo;
    private final SectionGroupRepo sectionGroupRepo;
    private final Utils utils;

    @Autowired
    public WorkServiceImpl(WorkRepo workRepo, UserRepo userRepo, SectionGroupRepo sectionGroupRepo, Utils utils) {
        this.workRepo = workRepo;
        this.userRepo = userRepo;
        this.sectionGroupRepo = sectionGroupRepo;
        this.utils = utils;
    }

    @Override
    public List<Work> getAllWork() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();


        List<Work> works = workRepo.findAll()
                .stream()
                .filter(o -> o.getDepartments().contains(userRepo.findByUsername(utils.getAuthUserName()).getDepartment()))
                .collect(Collectors.toList());
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

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//
//        Department department = departmentRepo.findDepartmentById(userRepo.findByUsername(name).getDepartment().getId());
//        List<Department> workDepartments = new ArrayList<>();
//        workDepartments.add(department);


        work.setCreatedDate(LocalDateTime.now());
        work.setStatus(Status.ACTIVE);
        work.setDepartments(utils.getDepartmentWithUser());
        work.setGroup(sectionGroupRepo.findSectionGroupById(work.getGroup().getId()));
        Work createdWork = workRepo.save(work);
        log.info("IN create - work: {} successfully added for department: {}", createdWork, createdWork.getDepartments());

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
