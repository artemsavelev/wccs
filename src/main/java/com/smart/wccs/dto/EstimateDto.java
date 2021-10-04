package com.smart.wccs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smart.wccs.model.Estimate;
import com.smart.wccs.model.Status;
import com.smart.wccs.model.User;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class EstimateDto {

    private Long id;
    private Long orderId;

    @Enumerated(EnumType.STRING)
    private Status key;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    private String extId;
    private String customer;
    private String address;
    private String workDescription;
    private String simpleText;
    private String author;
    private List<ComponentDto> devices;
    private List<ComponentDto> materials;
    private List<ComponentDto> works;
    private Double amount;

    public static EstimateDto fromEstimate(Estimate estimate) {
        List<ComponentDto> deviceDtoList = new ArrayList<>();
        List<ComponentDto> materialDtoList = new ArrayList<>();
        List<ComponentDto> workDtoList = new ArrayList<>();

        EstimateDto estimateDto = new EstimateDto();
        estimateDto.setId(estimate.getId());
        estimateDto.setOrderId(estimate.getOrder().getId());
        estimateDto.setKey(estimate.getStatus());
        estimateDto.setExtId(estimate.getExtId());
        estimateDto.setCreatedDate(estimate.getCreatedDate());
        estimateDto.setCustomer(estimate.getCustomer());
        estimateDto.setAddress(estimate.getAddress());
        estimateDto.setWorkDescription(estimate.getWorkDescription());
        estimateDto.setSimpleText(estimate.getSimpleText());
        estimateDto.setAuthor(estimate.getAuthor().getLastName() + " " + estimate.getAuthor().getFirstName());
        estimateDto.setAmount(estimate.getAmount());

        for (int i = 0; i < estimate.getEstimateDevices().size(); i++) {
            deviceDtoList.add(new ComponentDto(
                    estimate.getEstimateDevices().get(i).getDevice().getId(),
                    estimate.getEstimateDevices().get(i).getDevice().getName(),
                    estimate.getEstimateDevices().get(i).getDevice().getDimension(),
                    estimate.getEstimateDevices().get(i).getQuantity(),
                    estimate.getEstimateDevices().get(i).getDevice().getPrice()
            ));
        }

        for (int i = 0; i < estimate.getEstimateMaterials().size(); i++) {
            materialDtoList.add(new ComponentDto(
                    estimate.getEstimateMaterials().get(i).getMaterial().getId(),
                    estimate.getEstimateMaterials().get(i).getMaterial().getName(),
                    estimate.getEstimateMaterials().get(i).getMaterial().getDimension(),
                    estimate.getEstimateMaterials().get(i).getQuantity(),
                    estimate.getEstimateMaterials().get(i).getMaterial().getPrice()
            ));
        }

        for (int i = 0; i < estimate.getEstimateWorks().size(); i++) {
            workDtoList.add(new ComponentDto(
                    estimate.getEstimateWorks().get(i).getWork().getId(),
                    estimate.getEstimateWorks().get(i).getWork().getName(),
                    estimate.getEstimateWorks().get(i).getWork().getDimension(),
                    estimate.getEstimateWorks().get(i).getQuantity(),
                    estimate.getEstimateWorks().get(i).getWork().getPrice()
            ));
        }

        estimateDto.setDevices(deviceDtoList);
        estimateDto.setMaterials(materialDtoList);
        estimateDto.setWorks(workDtoList);

        return estimateDto;
    }

    public static List<EstimateDto> estimateDtoList(List<Estimate> estimates) {
        List<EstimateDto> estimateDtoList = new ArrayList<>();
        estimates.forEach(estimate -> estimateDtoList.add(fromEstimate(estimate)));
        return estimateDtoList;
    }
}
