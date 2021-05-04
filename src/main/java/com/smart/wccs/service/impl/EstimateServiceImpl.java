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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class EstimateServiceImpl implements EstimateService {

    private final EstimateRepo estimateRepo;
    private final FileCreator fileCreator;
    private final FileFactory fileFactory;
    private final UserRepo userRepo;
    private final Utils utils;

    @Autowired
    public EstimateServiceImpl(EstimateRepo estimateRepo, FileCreator fileCreator, FileFactory fileFactory, UserRepo userRepo, Utils utils) {
        this.estimateRepo = estimateRepo;
        this.fileCreator = fileCreator;
        this.fileFactory = fileFactory;
        this.userRepo = userRepo;
        this.utils = utils;
    }

    @Override
    public Estimate create(Estimate estimate) {

        User user = userRepo.findByUsername(utils.getAuthUserName());

        estimate.setCreatedDate(LocalDateTime.now());
        estimate.setStatus(Status.ACTIVE);
        estimate.setAuthor(user);
        estimate.setDepartment(user.getDepartment());

        Estimate createdEstimate = estimateRepo.save(estimate);

        // create excel file
        fileFactory.creator();
        fileCreator.createFile(estimate);

        log.info("IN create - estimate: {} successfully added", createdEstimate);
        return createdEstimate;
    }

    @Override
    public Estimate getById(Long id) {
        Estimate estimate = estimateRepo.findById(id).orElse(null);
        if (estimate == null) {
            log.warn("IN findById - no estimate found by id: {}", id);
            return null;
        }
        log.info("IN findById - estimate found by id: {}", estimate);
        return estimate;
    }

    @Override
    public Estimate update(Long id, Estimate estimate) {

        Estimate updatedEstimate = estimateRepo.findById(id).orElse(null);

        return null;
    }


}
