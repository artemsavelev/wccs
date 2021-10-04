package com.smart.wccs.repo;

import com.smart.wccs.dto.EstimateDto;
import com.smart.wccs.model.Estimate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstimateRepo extends JpaRepository<Estimate, Long> {

    Estimate getEstimateByOrderId(Long id);

    List<Estimate> getEstimatesByOrderId(Long id);

    Estimate findEstimateByExtId(String extId);

    List<Estimate> findEstimateByExtIdAndCustomerAndAddress(String extId, String customer, String address);

    List<Estimate> getEstimatesByCustomerAndAddress(String value, String address);
}
