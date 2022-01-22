package com.smart.wccs.service.filecreator.components;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class CellBuilder {

    private final Sheet sheet;
    private final CellType type = CellType.STRING;
    private Row row;
    private Cell cell;
    private final int firstCol = 0;

    public CellBuilder(Sheet sheet) {
        this.sheet = sheet;
    }

//    public CellBuilder mergedRegion(RowsTest row, Cols col) {
//        this.sheet.addMergedRegion(new CellRangeAddress(row.getRow(), row.getRow(), firstCol, col.getCol()));
//        return this;
//    }

    public CellBuilder mergedRegion(int row, int col) {
        this.sheet.addMergedRegion(new CellRangeAddress(row, row, firstCol, col));
        return this;
    }

    public CellBuilder row(int row) {
        this.row = sheet.createRow(row);
        return this;
    }

    public CellBuilder heightRow(int height) {
        this.row.setHeightInPoints((short) height); // высота строк
        return this;
    }

    public CellBuilder cell() {
        this.cell = this.row.createCell(this.firstCol);
        return this;
    }

    public CellBuilder cell(int col) {
        this.cell = this.row.createCell(col, this.type);
        return this;
    }

    public CellBuilder cell(int col, CellType type) {
        this.cell = this.row.createCell(col, type);
        return this;
    }

    public CellBuilder value(String value) {
        this.cell.setCellValue(value); // значение в ячейках
        return this;
    }

    public CellBuilder value(int value) {
        this.cell.setCellValue(value); // значение в ячейках
        return this;
    }

    public CellBuilder value(double value) {
        this.cell.setCellValue(value); // значение в ячейках
        return this;
    }

    public CellBuilder style(CellStyle style) {
        this.cell.setCellStyle(style); // стили
        return this;
    }

    //
    public CellBuilder cellFormula(int row) {
        String formula = "E" + row + "*D" + row;
        this.cell.setCellFormula(formula);
        return this;
    }

    // итог по секции
    public CellBuilder cellFormula(int firstRow, int lastRow) {
        String formula = "SUM(F" + firstRow + ":F" + lastRow + ")";
        this.cell.setCellFormula(formula);
        return this;
    }

    // итоговая формула
    public CellBuilder cellFormula(int firstSum, int secondSum,  int thirdSum) {
        String formula = "SUM(F" + firstSum + "+F" + secondSum + "+F" + thirdSum + ")";
        this.cell.setCellFormula(formula);
        return this;
    }

    /**
     * Формула вычета налога
     *
     * Посчитать НДС в том числе — это значит выделить налог, который заложен в итоговую сумму. Здесь для вычисления используют формулы:
     * НДС = С / 120 × 20 — если нужно посчитать НДС 20% (с 01.01.2019),
     * НДС = С / 118 × 18 — если нужно посчитать НДС 18% (до 01.01.2019),
     * НДС = С / 110 × 10 — если ставка налога 10%,
     * Где: С — сумма, включающая НДС.
     *
     * @param firstSum сумма по первому блоку (Активное оборудование)
     * @param secondSum сумма по второму блоку (Материалы и оборудование)
     * @param thirdSum сумма по третьему блоку (Работы)
     * @param tax налоговый процент
     * @return текущий контекст
     */
    public CellBuilder cellFormula(int firstSum, int secondSum,  int thirdSum, int tax) {
        String formula = "(F" + firstSum + "+F" + secondSum + "+F" + thirdSum + ")/" + (100 + tax) + "*" + tax;
        this.cell.setCellFormula(formula);
        return this;
    }

//    public void build() {
//        new CellBuilder(sheet);
//    }

}
