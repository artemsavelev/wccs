package com.smart.wccs.service.filecreator.components;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class CreateCell {
    private final Sheet sheet;
    private final CellType type;

    public CreateCell(Sheet sheet, CellType type) {
        this.sheet = sheet;
        this.type = type;
    }

    public void getCell(CellStyle style, int numRow, int firstCol, int lastCol, int height, String value) {
        sheet.addMergedRegion(new CellRangeAddress(numRow, numRow, firstCol, lastCol));
        Row row = sheet.createRow(numRow);
        row.setHeightInPoints((short) height); // высота строк
        Cell cell = row.createCell(0, type);
        cell.setCellValue(value); // значение в ячейках
        cell.setCellStyle(style); // стили
    }

    public void cellFormula(CellStyle style, int firstRow, int lastRow, int height) {
        String formula = "SUM(F" + firstRow + ":F" + lastRow + ")";
        Row row = sheet.getRow(lastRow);
        row.setHeightInPoints((short) height); // высота строк
        Cell cell = row.createCell(5, type);
        cell.setCellFormula(formula);
        style.setDataFormat((short) 0x27);
        cell.setCellStyle(style);
    }

    public void cellFormula(CellStyle style, int firstSum, int secondSum, int thirdSum, int tax,  int taxRow, int height) {
        String formula = "(F" + firstSum + "+F" + secondSum + "+F" + thirdSum + ")*" + tax + "/100";
        Row row = sheet.getRow(taxRow);
        row.setHeightInPoints((short) height); // высота строк
        Cell cell = row.createCell(5, type);
        cell.setCellFormula(formula);
        style.setDataFormat((short) 0x27);
        cell.setCellStyle(style);
    }

    public void cellFormula(CellStyle style, int firstSum, int secondSum, int thirdSum, int finalRow, int height) {
        String formula = "SUM(F" + firstSum + "+F" + secondSum + "+F" + thirdSum + ")";
        Row row = sheet.getRow(finalRow);
        row.setHeightInPoints((short) height); // высота строк
        Cell cell = row.createCell(5, type);
        cell.setCellFormula(formula);
        style.setDataFormat((short) 0x27);
        cell.setCellStyle(style);
    }
}
