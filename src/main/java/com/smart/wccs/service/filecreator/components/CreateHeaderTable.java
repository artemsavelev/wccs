package com.smart.wccs.service.filecreator.components;

import org.apache.poi.ss.usermodel.*;

public class CreateHeaderTable {
    private final Sheet sheet;
    private final CellType type;

    public CreateHeaderTable(Sheet sheet, CellType type) {
        this.sheet = sheet;
        this.type = type;
    }

    public void getHeader(CellStyle style, int numRow) {

        // создаем строку с наименованием колонок таблицы
        Row row = sheet.createRow(numRow + 1);

        // порядковый номер
        Cell cell = row.createCell(0, type);
        cell.setCellValue("#");
        cell.setCellStyle(style);

        // наименование
        cell = row.createCell(1, type);
        cell.setCellValue("Наименование");
        cell.setCellStyle(style);

        // единица измерения
        cell = row.createCell(2, type);
        cell.setCellValue("Ед. изм.");
        cell.setCellStyle(style);

        // количество
        cell = row.createCell(3, type);
        cell.setCellValue("Кол-во");
        cell.setCellStyle(style);

        // цена
        cell = row.createCell(4, type);
        cell.setCellValue("Цена");
        cell.setCellStyle(style);

        // сумма
        cell = row.createCell(5, type);
        cell.setCellValue("Сумма");
        cell.setCellStyle(style);
    }

}
