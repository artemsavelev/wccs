package com.smart.wccs.service.filecreator;

import com.smart.wccs.model.Components;
import com.smart.wccs.model.Estimate;
import com.smart.wccs.service.filecreator.components.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ExcelDocument implements FileCreator {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${tax.percent}")
    private int tax;


    @Override
    public void createFile(Estimate estimate) {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("sheet");
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 8);
        font.setFontName("Montserrat");
        List<Components> listDevice = new ArrayList<>(estimate.getDevices());
        List<Components> listMaterial = new ArrayList<>(estimate.getMaterials());
        List<Components> listWork = new ArrayList<>(estimate.getWorks());

        CreateStyle createStyle = new CreateStyle(workbook, font);
        CellStyle styleHeaderTable = createStyle.getStyle(IndexedColors.GREY_25_PERCENT, FillPatternType.SOLID_FOREGROUND,
                HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        CellStyle styleEmpty = createStyle.getStyle();
        CellStyle styleAlignCenter = createStyle.getStyle(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        CellStyle styleAlignCenter2 = createStyle.getStyle(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        CellStyle styleAlignCenterTop = createStyle.getStyle(HorizontalAlignment.LEFT, VerticalAlignment.TOP);
        CellStyle styleAlignCenterBold = createStyle.getStyle(true, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        CellStyle styleAlignLeft = createStyle.getStyle(HorizontalAlignment.LEFT, VerticalAlignment.CENTER);
        CellStyle styleAlignLeftColor = createStyle.getStyle(IndexedColors.RED, HorizontalAlignment.LEFT, VerticalAlignment.CENTER);
        CellStyle styleAlignRightBold = createStyle.getStyle(true, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER);
        CellStyle styleAlignLeftBottomBold = createStyle.getStyle(true,  HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM);

        new SheetSettings(sheet).getSettings();

        CreateCell createCell = new CreateCell(sheet, CellType.STRING);
        CreateHeaderTable headerTable = new CreateHeaderTable(sheet, CellType.STRING);
        Data data = new Data(workbook, sheet, font, styleAlignCenter);

        createCell.getCell(styleAlignCenter2,0, 0, 5, 30, "Смета");
        createCell.getCell(styleAlignCenter2,1, 0, 5, 15, "№: " + estimate.getExtId());
        createCell.getCell(styleAlignCenter2,2, 0, 5, 15, "адрес: " + estimate.getAddress());
        createCell.getCell(styleAlignCenter2,3, 0, 5, 15, "заказчик: " + estimate.getCustomer());
        createCell.getCell(styleAlignCenter2,4, 0, 5, 15, "составил: " +
                estimate.getAuthor().getLastName() + " " + estimate.getAuthor().getFirstName() + " " +
                estimate.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        createCell.getCell(styleEmpty, 5, 0, 5, 15, "");
        createCell.getCell(styleAlignLeft, 6, 0, 5, 15, "Описание работ :");
        createCell.getCell(styleAlignCenterTop, 7, 0, 5, 100, estimate.getWorkDescription());
        createCell.getCell(styleAlignLeftColor, 8, 0, 5, 70, estimate.getSimpleText());

        createCell.getCell(styleAlignLeftBottomBold, 9, 0, 5, 25, "1. Активное оборудование");
        headerTable.getHeader(styleHeaderTable, 9);

        // Data device
        int row1 = 10;
        row1 = data.getData(row1, listDevice);

        createCell.getCell(styleAlignRightBold, row1 + 1, 0, 4, 15, "ИТОГО за активное оборудование: ");
        createCell.cellFormula(styleAlignCenterBold, row1-listDevice.size()+2, row1 + 1, 15);
        createCell.getCell(styleAlignLeftBottomBold, row1 + 2, 0, 5, 25, "2. Материалы и оборудование");
        headerTable.getHeader(styleHeaderTable,row1 + 2);

        // Data material
        int row2 = row1 + 3;
        row2 = data.getData(row2, listMaterial);

        createCell.getCell(styleAlignRightBold, row2 + 1, 0, 4, 15, "ИТОГО за материалы и оборудование: ");
        createCell.cellFormula(styleAlignCenterBold,row2-listMaterial.size()+2, row2 + 1, 15);
        createCell.getCell(styleAlignLeftBottomBold, row2 + 2, 0, 5, 25, "3. Работы");
        headerTable.getHeader(styleHeaderTable,row2 + 2);

        // Data work
        int row3 = row2 + 3;
        row3 = data.getData(row3, listWork);

        createCell.getCell(styleAlignRightBold, row3 + 1, 0, 4, 15, "ИТОГО за работы: ");
        createCell.cellFormula(styleAlignCenterBold,row3-listWork.size()+2, row3 + 1, 15);
        createCell.getCell(styleEmpty,row3 + 2, 0, 5, 7, "");

        createCell.getCell(styleAlignRightBold,row3 + 3, 0, 4, 15, "В том числе НДС " + tax + "%: ");
        createCell.cellFormula(styleAlignCenterBold,row1 + 2, row2 + 2, row3 + 2, tax,row3 + 3, 15);

        createCell.getCell(styleAlignRightBold,row3 + 4, 0, 4, 15, "ИТОГО ОБЩАЯ СУММА: ");
        createCell.cellFormula(styleAlignCenterBold,row1 + 2, row2 + 2, row3 + 2, row3 + 4, 15);


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
