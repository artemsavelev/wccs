package com.smart.wccs.service.filecreator.builder.files;

import com.smart.wccs.service.filecreator.builder.components.Sheet;

/**
 * Смета — это класс продукта.
 */

public class EstimateFile {
    private final Sheet sheet;

    public EstimateFile(Sheet sheet) {
        this.sheet = sheet;
    }

    public Sheet getSheet() {
        return sheet;
    }
}
