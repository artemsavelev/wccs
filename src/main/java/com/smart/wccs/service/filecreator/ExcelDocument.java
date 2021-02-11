package com.smart.wccs.service.filecreator;

import com.smart.wccs.model.Estimate;
import com.smart.wccs.service.filecreator.builder.Data;
import com.smart.wccs.service.filecreator.builder.HeaderTable;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@Component
public class ExcelDocument implements FileCreator {

    @Value("${upload.path}")
    private String uploadPath;


    @Override
    public void createFile(Estimate estimate) {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("sheet");

        sheet.setFitToPage(true);
        sheet.setColumnWidth(0, 1000);
        sheet.setColumnWidth(1, 15000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 3500);
        sheet.setColumnWidth(4, 3500);
        sheet.setColumnWidth(5, 3500);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle style1 = workbook.createCellStyle();
        style1.setWrapText(true);
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle styleHeader = workbook.createCellStyle();

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
        Row rowTitle = sheet.createRow(0);
        rowTitle.setHeight((short) 1000);
        rowTitle.setRowStyle(style1);
        rowTitle.createCell(0).setCellValue("Смета");

        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));
        Row rowExtId = sheet.createRow(1);
        rowExtId.setRowStyle(style1);
        rowExtId.createCell(0).setCellValue("Запрос на смету : " + estimate.getExtId());

        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));
        Row rowAddress = sheet.createRow(2);
        rowAddress.setRowStyle(style1);
        rowAddress.createCell(0).setCellValue("Адрес : " + estimate.getAddress());

        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 5));
        Row rowCustomer = sheet.createRow(3);
        rowCustomer.setRowStyle(style1);
        rowCustomer.createCell(0).setCellValue("Заказчик : " + estimate.getCustomer());

        sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 5));
        sheet.createRow(4);

        sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 5));
        Row workDescription = sheet.createRow(5);
        workDescription.setRowStyle(style);
        workDescription.createCell(0).setCellValue("Описание работ :");

        sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 5));
        Row workDescriptionItem = sheet.createRow(6);
        workDescriptionItem.setRowStyle(style);
        workDescriptionItem.setHeightInPoints((short) 100);
        workDescriptionItem.createCell(0).setCellValue(estimate.getWorkDescription());


        sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 5));
        Row row4 = sheet.createRow(7);
        row4.setRowStyle(style);
        row4.setHeight((short) 1000);



        HeaderTable.getHeader(sheet, style, styleHeader, 8);
        Data.getData(sheet, style, 9, estimate.getDevices());




        try {

            // проверяем существует ли папка
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }


            File file = new File(uploadPath + File.separator +
                    estimate.getAddress() + " " +
                    estimate.getCustomer() + " " +
                    estimate.getExtId() + ".xlsx");

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
