package com.smart.wccs.service.impl;

import com.smart.wccs.dto.EstimateDto;
import com.smart.wccs.model.*;
import com.smart.wccs.repo.*;
import com.smart.wccs.service.EstimateService;
import com.smart.wccs.service.OrderService;
import com.smart.wccs.service.filecreator.FileCreator;
import com.smart.wccs.service.filecreator.FileFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EstimateServiceImpl implements EstimateService {

    private final EstimateRepo estimateRepo;
    private final FileCreator fileCreator;
    private final FileFactory fileFactory;
    private final UserRepo userRepo;
    private final Utils utils;
    private final OrderService orderService;
    private final EstimateDevicesRepo estimateDevicesRepo;
    private final EstimateMaterialsRepo estimateMaterialsRepo;
    private final EstimateWorksRepo estimateWorksRepo;


    @Autowired
    public EstimateServiceImpl(EstimateRepo estimateRepo,
                               FileCreator fileCreator,
                               FileFactory fileFactory,
                               UserRepo userRepo, Utils utils,
                               OrderService orderService,
                               EstimateDevicesRepo estimateDevicesRepo,
                               EstimateMaterialsRepo estimateMaterialsRepo,
                               EstimateWorksRepo estimateWorksRepo) {
        this.estimateRepo = estimateRepo;
        this.fileCreator = fileCreator;
        this.fileFactory = fileFactory;
        this.userRepo = userRepo;
        this.utils = utils;
        this.orderService = orderService;
        this.estimateDevicesRepo = estimateDevicesRepo;
        this.estimateMaterialsRepo = estimateMaterialsRepo;
        this.estimateWorksRepo = estimateWorksRepo;
    }

    @Override
    public Estimate create(Estimate estimate) {

        User user = userRepo.findByUsername(utils.getAuthUserName());
        Order order = orderService.getById(estimate.getOrder().getId());

        estimate.setCreatedDate(LocalDateTime.now());

        if ("ПРЕДВАРИТЕЛЬНАЯ".equals(estimate.getKey())) {
            estimate.setStatus(Status.ACTIVE);
            if (order.getVersionEstimate() != Status.COMPLETED) {
                order.setVersionEstimate(Status.PROCESS); // меняем статус в задачах
            }
        } else {
            estimate.setStatus(Status.COMPLETED);
            order.setVersionEstimate(Status.COMPLETED); // меняем статус в задачах
        }

        estimate.setAuthor(user);
        estimate.setDepartment(user.getDepartment());

        // итоговая сумма
        estimate.setAmount(
                sumComponents(new ArrayList<>(estimate.getDevices())) +
                sumComponents(new ArrayList<>(estimate.getMaterials())) +
                sumComponents(new ArrayList<>(estimate.getWorks()))
        );

        Estimate createdEstimate = estimateRepo.saveAndFlush(estimate);

        for (int i = 0; i < createdEstimate.getDevices().size(); i++) {
            estimateDevicesRepo.saveAndFlush(new EstimateDevices(
                    createdEstimate,
                    createdEstimate.getDevices().get(i),
                    createdEstimate.getDevices().get(i).getQuantity()
            ));
        }

        for (int i = 0; i < createdEstimate.getMaterials().size(); i++) {
            estimateMaterialsRepo.saveAndFlush(new EstimateMaterials(
                    createdEstimate,
                    createdEstimate.getMaterials().get(i),
                    createdEstimate.getMaterials().get(i).getQuantity()
            ));
        }

        for (int i = 0; i < createdEstimate.getWorks().size(); i++) {
            estimateWorksRepo.saveAndFlush(new EstimateWorks(
                    createdEstimate,
                    createdEstimate.getWorks().get(i),
                    createdEstimate.getWorks().get(i).getQuantity()
            ));
        }

        log.info("IN create - estimate: {} successfully added", createdEstimate);
        return createdEstimate;
    }

    public double sumComponents(List<Components> components) {
        double scale = Math.pow(10, 2);
        return Math.ceil(components
                .stream()
                .map(c -> (c.getPrice() * c.getQuantity()))
                .reduce(0.0, Double::sum) * scale) / scale;
    }

    @Override
    public String getFileName(String extId, String customer, String address) {

        List<Estimate> estimates = estimateRepo.findEstimateByExtIdAndCustomerAndAddress(extId, customer, address)
                .stream()
                .sorted(Comparator.comparing(Estimate::getId))
                .collect(Collectors.toList());

        EstimateDto estimateDto = EstimateDto.estimateDtoList(estimates)
                .stream()
                .reduce((a, b) -> b)
                .orElseThrow();

        // create excel file
        fileFactory.creator();
        String filename = fileCreator.createFile(estimateDto);

        log.info("IN getFileName {}", filename);
        return filename;
    }

    @Override
    public Estimate getById(Long id) {
        Estimate estimate = estimateRepo.getById(id);
        log.info("IN findById - estimate found by id: {}", estimate);
        return estimate;
    }

    @Override
    public List<Estimate> getEstimate(String customer, String address) {
        List<Estimate> estimates = estimateRepo.getEstimatesByCustomerAndAddress(customer, address)
                .stream()
                .sorted(Comparator.comparing(Estimate::getId))
                .limit(30)
                .filter(status -> status.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList());

        log.info("IN getEstimate - estimate found: {}", estimates);
        return estimates;
    }

    @Override
    public Estimate update(Long id, Estimate estimate) {
        Estimate updatedEstimate = estimateRepo.findById(id).orElse(null);
        return null;
    }


}
