package com.smart.wccs.controller;

import com.smart.wccs.model.Estimate;
import com.smart.wccs.service.EstimateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/")
public class EstimateController {

    private final EstimateService estimateService;

    @Autowired
    public EstimateController(EstimateService estimateService) {
        this.estimateService = estimateService;
    }

    @RequestMapping(value = "/createEstimate", method = RequestMethod.POST)
    public ResponseEntity<Estimate> saveEstimate(@RequestBody Estimate estimate) {

        if (estimate == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        estimateService.create(estimate);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
