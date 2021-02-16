package com.smart.wccs.service.filecreator.builder.builders;


import com.smart.wccs.service.filecreator.builder.components.Sheet;
import com.smart.wccs.service.filecreator.builder.files.EstimateFile;

/**
 * Конкретные строители реализуют шаги, объявленные в общем интерфейсе.
 */


public class EstimateBuilder implements Builder {
    private Sheet sheet;


    @Override
    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public EstimateFile getFile() {
        return new EstimateFile(sheet);
    }

}
