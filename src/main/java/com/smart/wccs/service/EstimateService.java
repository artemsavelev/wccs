package com.smart.wccs.service;

import com.smart.wccs.model.Estimate;

public interface EstimateService {

    Estimate create(Estimate estimate);

    Estimate getById(Long id);

    Estimate update(Long id, Estimate estimate);


}
