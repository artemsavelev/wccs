package com.smart.wccs.service.impl;

import com.smart.wccs.model.Estimate;
import com.smart.wccs.model.Status;
import com.smart.wccs.model.User;
import com.smart.wccs.repo.EstimateRepo;
import com.smart.wccs.repo.UserRepo;
import com.smart.wccs.service.EstimateService;
import com.smart.wccs.service.filecreator.FileCreator;
import com.smart.wccs.service.filecreator.FileFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class EstimateServiceImpl implements EstimateService {

    private final EstimateRepo estimateRepo;
    private final FileCreator fileCreator;
    private final FileFactory fileFactory;
    private final UserRepo userRepo;

    @Autowired
    public EstimateServiceImpl(EstimateRepo estimateRepo, FileCreator fileCreator, FileFactory fileFactory, UserRepo userRepo) {
        this.estimateRepo = estimateRepo;
        this.fileCreator = fileCreator;
        this.fileFactory = fileFactory;
        this.userRepo = userRepo;
    }

    @Override
    public Estimate create(Estimate estimate) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userRepo.findByUsername(name);

        estimate.setCreatedDate(LocalDateTime.now());
        estimate.setStatus(Status.COMPLETED);
        estimate.setDepartment(user.getDepartment());

        Estimate createdEstimate = estimateRepo.save(estimate);

        // create excel file
        fileFactory.creator();
        fileCreator.createFile(estimate);

        log.info("IN create - estimate: {} successfully added", createdEstimate);
        return createdEstimate;
    }
}
