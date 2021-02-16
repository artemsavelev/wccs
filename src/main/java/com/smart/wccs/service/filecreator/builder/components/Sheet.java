package com.smart.wccs.service.filecreator.builder.components;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * Одна из фишек файла.
 */

public class Sheet {
    private Workbook workbook;
    private String name;

    public Sheet(Workbook workbook, String name) {
        this.workbook = workbook;
        this.name = name;
    }
}
