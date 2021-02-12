package com.smart.wccs.service.filecreator;

import com.smart.wccs.model.Device;
import com.smart.wccs.model.Estimate;
import com.smart.wccs.model.Material;
import com.smart.wccs.model.Work;
import com.smart.wccs.service.filecreator.builder.Data;
import com.smart.wccs.service.filecreator.builder.HeaderTable;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class ExcelDocument implements FileCreator {

    @Value("${upload.path}")
    private String uploadPath;


    @Override
    public void createFile(Estimate estimate) {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("sheet");
        Font font = new XSSFFont();
        font.setFontName("Montserrat");

        sheet.setFitToPage(true);
        sheet.setColumnWidth(0, 1000);
        sheet.setColumnWidth(1, 15000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 3500);
        sheet.setColumnWidth(4, 3500);
        sheet.setColumnWidth(5, 3500);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        style.setFont(font);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle style1 = workbook.createCellStyle();
        style1.setWrapText(true);
        style1.setFont(font);
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
        Row simpleText = sheet.createRow(7);
        simpleText.setRowStyle(style);
        simpleText.setHeight((short) 1000);
        simpleText.createCell(0).setCellValue(estimate.getSimpleText());

        HeaderTable.getHeader(sheet, style, styleHeader, 8);
//        Data.getData(sheet, style, 9, estimate.getDevices());


        List<Device> listDevice = estimate.getDevices();
        // Data device
        int row1 = 9;
        int i = 1;
        for (Device dev : listDevice) {
            row1++;
            i++;
            Row rowDevice = sheet.createRow(row1);
            // # (A)
            rowDevice.createCell(0, CellType.NUMERIC).setCellValue(i);
            rowDevice.setRowStyle(style);
            // name (B)
            rowDevice.createCell(1, CellType.STRING).setCellValue(dev.getName());
            rowDevice.setRowStyle(style);
            // dimension (C)
            rowDevice.createCell(2, CellType.STRING).setCellValue(dev.getDimension());
            rowDevice.setRowStyle(style);
            // quantity (D)
            rowDevice.createCell(3, CellType.NUMERIC).setCellValue(dev.getQuantity());
            rowDevice.setRowStyle(style);

            // price (E)
            rowDevice.createCell(4, CellType.BOOLEAN).setCellValue(dev.getPrice());
            rowDevice.setRowStyle(style);
            // sum (F)


        }
        sheet.addMergedRegion(new CellRangeAddress(row1 + 1, row1 + 1, 0, 4));


        List<Material> listMaterial = estimate.getMaterials();
        // Data material
        int row2 = row1 + estimate.getDevices().size() + 1;
        HeaderTable.getHeader(sheet, style, styleHeader, row2 - 1);
        log.info("row2 {}", row2);
        for (Material mat : listMaterial) {
            row2++;
            Row rowMaterial = sheet.createRow(row2);
            // # (A)
            rowMaterial.createCell(0, CellType.NUMERIC).setCellValue(mat.getId());
            rowMaterial.setRowStyle(style);
            // name (B)
            rowMaterial.createCell(1, CellType.STRING).setCellValue(mat.getName());
            rowMaterial.setRowStyle(style);
            // dimension (C)
            rowMaterial.createCell(2, CellType.STRING).setCellValue(mat.getDimension());
            rowMaterial.setRowStyle(style);
            // quantity (D)
            rowMaterial.createCell(3, CellType.NUMERIC).setCellValue(mat.getQuantity());
            rowMaterial.setRowStyle(style);

            // price (E)
            rowMaterial.createCell(4, CellType.BOOLEAN).setCellValue(mat.getPrice());
            rowMaterial.setRowStyle(style);
            // sum (F)


        }
        sheet.addMergedRegion(new CellRangeAddress(row2 + 1, row2 + 1, 0, 4));


        List<Work> listWork = estimate.getWorks();
        // Data work
        int row3 = row2 + estimate.getMaterials().size() + 1;
        HeaderTable.getHeader(sheet, style, styleHeader, row3 - 1);
        log.info("row3 {}", row3);
        for (Work work : listWork) {
            row3++;
            Row rowWork = sheet.createRow(row3);

            // # (A)
            rowWork.createCell(0, CellType.NUMERIC).setCellValue(work.getId());
            rowWork.setRowStyle(style);
            // name (B)
            rowWork.createCell(1, CellType.STRING).setCellValue(work.getName());
            rowWork.setRowStyle(style);
            // dimension (C)
            rowWork.createCell(2, CellType.STRING).setCellValue(work.getDimension());
            rowWork.setRowStyle(style);
            // quantity (D)
            rowWork.createCell(3, CellType.NUMERIC).setCellValue(work.getQuantity());
            rowWork.setRowStyle(style);

            // price (E)
            rowWork.createCell(4, CellType.BOOLEAN).setCellValue(work.getPrice());
            rowWork.setRowStyle(style);
            // sum (F)


        }
        sheet.addMergedRegion(new CellRangeAddress(row3 + 1, row3 + 1, 0, 4));


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
