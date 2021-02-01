package com.smart.wccs.service.impl;

import com.smart.wccs.model.Estimate;
import com.smart.wccs.model.Status;
import com.smart.wccs.repo.EstimateRepo;
import com.smart.wccs.service.EstimateService;
import com.smart.wccs.service.filecreator.ExcelFileFactory;
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

    @Autowired
    public EstimateServiceImpl(EstimateRepo estimateRepo) {
        this.estimateRepo = estimateRepo;
    }

    @Override
    public void create(Estimate estimate) {
        estimate.setCreatedDate(LocalDateTime.now());
        estimate.setStatus(Status.COMPLETED);
        Estimate createdEstimate = estimateRepo.save(estimate);

        // create excel file
        FileFactory fileFactory = new ExcelFileFactory();
        FileCreator fileCreator = fileFactory.creator();
        fileCreator.createFile(estimate);

        log.info("IN create - estimate: {} successfully added", createdEstimate);
    }
}
