package com.smart.wccs.service.filecreator;

import com.smart.wccs.model.Estimate;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class ExcelDocument implements FileCreator {

    private int rowNum = 0;
    private Cell cell;
    private Row row;


    @Override
    public void createFile(Estimate estimate) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("sheet");


        //
        XSSFCellStyle style = createStyleForTitle(workbook);

        row = sheet.createRow(rowNum);

        // EmpNo
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("EmpNo");
        cell.setCellStyle(style);
        // EmpName
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("EmpNo");
        cell.setCellStyle(style);
        // Salary
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Salary");
        cell.setCellStyle(style);
        // Grade
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Grade");
        cell.setCellStyle(style);
        // Bonus
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Bonus");
        cell.setCellStyle(style);

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
            File file = new File("employee.xlsx");

            if (file.createNewFile()) {
                log.info("IN createFile - create estimate {} : successfully created in {} ", file.getName(), file.getAbsolutePath());
            } else {
                log.info("IN createFile - create estimate {} : error created", file.getName());
            }

            FileOutputStream outFile = new FileOutputStream(file);
            workbook.write(outFile);




        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static XSSFCellStyle createStyleForTitle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }
}
