package com.smart.wccs.service.filecreator.builder;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class HeaderTable {
    public static void getHeader(Sheet sheet, CellStyle style, CellStyle styleHeader, int numRow) {

        // объеденяем ячейки первой строки
        sheet.addMergedRegion(new CellRangeAddress(numRow, numRow, 0, 5));

        // создаем строку с наименованием секции
        Row row = sheet.createRow(numRow);
        row.setHeight((short) 500);
        row.createCell(0).setCellValue("Test test 1");
        row.setRowStyle(style);

        // создаем строку с наименованием колонок таблицы
        row = sheet.createRow(numRow + 1);
        row.setRowStyle(style);

        // порядковый номер
        Cell cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("#");
        cell.setCellStyle(styleHeader);
        // нименование
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Наименование");
        cell.setCellStyle(styleHeader);
        // еденица измерения
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Ед. изм.");
        cell.setCellStyle(styleHeader);
        // количество
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Кол-во");
        cell.setCellStyle(styleHeader);
        // цена
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Цена");
        cell.setCellStyle(styleHeader);
        // сумма
        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Сумма");
        cell.setCellStyle(styleHeader);
    }

}
