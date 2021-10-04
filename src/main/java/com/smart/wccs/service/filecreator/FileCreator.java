package com.smart.wccs.service.filecreator;

import com.smart.wccs.dto.EstimateDto;

public interface FileCreator {
    String createFile(EstimateDto estimate);
}
