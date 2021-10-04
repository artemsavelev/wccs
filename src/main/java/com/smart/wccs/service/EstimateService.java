package com.smart.wccs.service;

import com.smart.wccs.model.Estimate;

import java.util.List;

/**
 *
 *
 */
public interface EstimateService {

    Estimate create(Estimate estimate);

    Estimate getById(Long id);

    List<Estimate> getEstimate(String customer, String address);

    String getFileName(String extId, String customer, String address);

    Estimate update(Long id, Estimate estimate);
}
