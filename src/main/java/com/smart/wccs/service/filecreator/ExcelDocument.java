package com.smart.wccs.service.filecreator;

import com.smart.wccs.model.Estimate;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@Component
public class ExcelDocument implements FileCreator {

    private final XSSFWorkbook workbook;
    private final Sheet sheet;
    private XSSFCellStyle style;
    private int rowNum;
    private Cell cell;
    private Row row;

    public ExcelDocument() {
        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet("sheet");
        this.rowNum = 0;
        this.row = sheet.createRow(rowNum);
        this.style = createStyleForTitle(workbook);
    }

    private XSSFCellStyle createStyleForTitle(XSSFWorkbook workbook) {
        style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
//        font.setBold(true);
        font.setFontName("Montserrat");
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);
        return style;
    }

    public void createHeaderTable() {

        // объеденяем ячейки первой строки
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
        sheet.setColumnWidth(1, 15000);




        // создаем строку с наименованием секции
        row = sheet.createRow(0);
        row.setRowStyle(style);
        cell = row.createCell(0);
        cell.setCellValue("Test test"); // наименование секции

        row = sheet.createRow(1);
        row.setRowStyle(style);

        // порядковый номер
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("#");
        cell.setCellStyle(style);
        // нименование
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Наименование");
        // еденица измерения
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Ед. изм.");
        // количество
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Кол-во");
        // цена
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Цена");
        // сумма
        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Сумма");
    }


    @Override
    public void createFile(Estimate estimate) {

        createHeaderTable();

        // Data
//        for (Estimate est : estimate) {
//            rowNum++;
//            row = sheet.createRow(rowNum);
//
//            // EmpNo (A)
//            cell = row.createCell(0, CellType.STRING);
//            cell.setCellValue(est.getExtId());
//            // EmpName (B)
//            cell = row.createCell(1, CellType.STRING);
//            cell.setCellValue(est.getCustomer());
//            // Salary (C)
//            cell = row.createCell(2, CellType.NUMERIC);
//            cell.setCellValue(est.getAddress());
//            // Grade (D)
//            cell = row.createCell(3, CellType.NUMERIC);
//            cell.setCellValue(est);
//            // Bonus (E)
//            String formula = "0.1*C" + (rowNum + 1) + "*D" + (rowNum + 1);
//            cell = row.createCell(4, CellType.FORMULA);
//            cell.setCellFormula(formula);
//        }

        try {
            File file = new File("upload" + File.separator + "employee.xlsx");


            if (file.createNewFile()) {
                log.info("IN createFile - create estimate {} : successfully created in {} ", file.getName(), file.getAbsolutePath());
            } else {
                log.info("IN createFile - create estimate {} : error created", file.getName());
            }

            FileOutputStream outFile = new FileOutputStream(file);
            workbook.write(outFile);

            outFile.close();


        } catch (IOException e) {
            log.error("IN createFile - create estimate {} : error {}", e.getMessage(), e.getStackTrace());
        }


    }

}
