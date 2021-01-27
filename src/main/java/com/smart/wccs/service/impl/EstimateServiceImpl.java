package com.smart.wccs.service.impl;

import com.smart.wccs.model.Estimate;
import com.smart.wccs.model.Status;
import com.smart.wccs.repo.EstimateRepo;
import com.smart.wccs.service.EstimateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Slf4j
public class EstimateServiceImpl implements EstimateService {

    private final EstimateRepo estimateRepo;

    @Autowired
    public EstimateServiceImpl(EstimateRepo estimateRepo) {
        this.estimateRepo = estimateRepo;
    }

    @Override
    public void create(Estimate estimate) {
        estimate.setCreatedDate(LocalDateTime.now());
        estimate.setStatus(Status.COMPLETED);
        Estimate createdEstimate = estimateRepo.save(estimate);
        log.info("IN create - estimate: {} successfully added", createdEstimate);
    }
}
