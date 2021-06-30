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

    private String keyEstimate;

    private final int fontDefaultSize = 8;

    private int fontTitleSize = 12;

    private Workbook workbook;

    private Sheet sheet;

    @Override
    public void createFile(Estimate estimate) {

        if ("ПРЕДВАРИТЕЛЬНАЯ".equals(estimate.getKey())) {
            keyEstimate = "ПРЕДВАРИТЕЛЬНАЯ";
        } else {
            keyEstimate = "ФАКТИЧЕСКАЯ";
        }

        workbook = new XSSFWorkbook();

        sheet = workbook.createSheet("Смета " + keyEstimate);

        if (workbook instanceof XSSFWorkbook) {
            ((XSSFWorkbook) workbook).getProperties().getCoreProperties().setCreator(estimate.getAuthor().toString());
        }

        List<Components> listDevice = new ArrayList<>(estimate.getDevices());
        List<Components> listMaterial = new ArrayList<>(estimate.getMaterials());
        List<Components> listWork = new ArrayList<>(estimate.getWorks());

        new SheetSettings(sheet).getSettings();

        new CellBuilder(sheet)
                // первая строка
                .mergedRegion(Rows.FIRST_ROW, Cols.SIXTH_COL)
                .row(Rows.FIRST_ROW.getRow())
                .heightRow(HeightCell.HEIGHT_CELL_TITLE.getIndex())
                .cell()
                .value("ФАКТИЧЕСКАЯ".equals(keyEstimate) ? "Смета" : "Смета " + keyEstimate)
                .style(getStyle(fontTitleSize))

                // вторая строка
                .mergedRegion(Rows.SECOND_ROW, Cols.SIXTH_COL)
                .row(Rows.SECOND_ROW.getRow())
                .heightRow(HeightCell.HEIGHT_CELL_DEFAULT.getIndex())
                .cell()
                .value("№: " + estimate.getExtId())
                .style(getStyle(fontDefaultSize))

                // третья строка
                .mergedRegion(Rows.THIRD_ROW, Cols.SIXTH_COL)
                .row(Rows.THIRD_ROW.getRow())
                .cell()
                .value("адрес: " + estimate.getAddress())
                .style(getStyle(fontDefaultSize))

                // четвертая строка
                .mergedRegion(Rows.FOURTH_ROW, Cols.SIXTH_COL)
                .row(Rows.FOURTH_ROW.getRow())
                .cell()
                .value("заказчик: " + estimate.getCustomer())
                .style(getStyle(fontDefaultSize))

                // пятая строка
                .mergedRegion(Rows.FIFTH_ROW, Cols.SIXTH_COL)
                .row(Rows.FIFTH_ROW.getRow())
                .cell()
                .value("составил: " +
                        estimate.getAuthor().getLastName() + " " +
                        estimate.getAuthor().getFirstName() + " " +
                        estimate.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .style(getStyle(fontDefaultSize))

                // шестая строка
                .mergedRegion(Rows.SIXTH_ROW, Cols.SIXTH_COL)
                .row(Rows.SIXTH_ROW.getRow())
                .cell()
                .value("")
                .style(getStyle(fontDefaultSize))

                // седьмая строка
                .mergedRegion(Rows.SEVENTH_ROW, Cols.SIXTH_COL)
                .row(Rows.SEVENTH_ROW.getRow())
                .cell()
                .value("Описание работ :")
                .style(new StyleBuilder(workbook)
                        .font(new FontBuilder(workbook)
                                .fontName()
                                .fontSize(fontDefaultSize)
                                .buildFont())
                        .verticalAlign(VerticalAlignment.CENTER)
                        .horizontalAlign(HorizontalAlignment.LEFT)
                        .buildStyle())

                // восьмая строка
                .mergedRegion(Rows.EIGHTH_ROW, Cols.SIXTH_COL)
                .row(Rows.EIGHTH_ROW.getRow())
                .heightRow(HeightCell.HEIGHT_CELL_DESCRIPTION.getIndex())
                .cell()
                .value(estimate.getWorkDescription())
                .style(new StyleBuilder(workbook)
                        .font(new FontBuilder(workbook)
                                .fontName()
                                .fontSize(fontDefaultSize)
                                .buildFont())
                        .verticalAlign(VerticalAlignment.TOP)
                        .horizontalAlign(HorizontalAlignment.LEFT)
                        .wrapText(true)
                        .buildStyle())

                // девятая строка
                .mergedRegion(Rows.NINTH_ROW, Cols.SIXTH_COL)
                .row(Rows.NINTH_ROW.getRow())
                .heightRow(HeightCell.HEIGHT_CELL_TEXT.getIndex())
                .cell()
                .value(estimate.getSimpleText())
                .style(new StyleBuilder(workbook)
                        .font(new FontBuilder(workbook)
                                .fontName()
                                .fontSize(fontDefaultSize)
                                .fontColor(IndexedColors.RED)
                                .buildFont())
                        .verticalAlign(VerticalAlignment.CENTER)
                        .horizontalAlign(HorizontalAlignment.LEFT)
                        .wrapText(true)
                        .buildStyle());


        // секция (Активное оборудование)
        sectionHeader(Rows.TENTH_ROW.getRow(), "1. Активное оборудование");
        headerTable(Rows.ELEVENTH_ROW.getRow());

        // Data device
        new Data(workbook, sheet).getData(Rows.ELEVENTH_ROW.getRow(), listDevice);
        int startRowDevice = Rows.TWELFTH_ROW.getRow() + listDevice.size();
        totalSum(Rows.TWELFTH_ROW.getRow(), startRowDevice, "ИТОГО за активное оборудование: ");
        int lastRowDevice = startRowDevice + 1;




        // секция (Материалы и оборудование)
        sectionHeader(lastRowDevice, "2. Материалы и оборудование");
        headerTable(lastRowDevice + 1);

        // Data material
        new Data(workbook, sheet).getData(lastRowDevice + 1, listMaterial);
        int startRowMaterial = lastRowDevice + listMaterial.size() + 2;
        totalSum(lastRowDevice + 2, startRowMaterial, "ИТОГО за материалы и оборудование: ");
        int lastRowMaterial = startRowMaterial + 1;




        // секция (Работы)
        sectionHeader(lastRowMaterial, "3. Работы");
        headerTable(lastRowMaterial + 1);

        // Data work
        new Data(workbook, sheet).getData(lastRowMaterial + 1, listWork);
        int startRowWork = lastRowMaterial + listWork.size() + 2;
        totalSum(lastRowMaterial + 2, startRowWork, "ИТОГО за работы: ");
        int lastRowWork = startRowWork + 1;




        new CellBuilder(sheet)
                .mergedRegion(lastRowWork, Cols.SIXTH_COL)
                .row(lastRowWork)
                .heightRow(HeightCell.HEIGHT_CELL_EMPTY.getIndex())
                .cell()
                .value("")
                .style(getStyle(fontDefaultSize));



        new CellBuilder(sheet)
                // total tax
                .mergedRegion(lastRowWork + 1, Cols.FIFTH_COL)
                .row(lastRowWork + 1)
                .heightRow(HeightCell.HEIGHT_CELL_DEFAULT.getIndex())
                .cell()
                .value("В том числе НДС " + tax + "%: ")
                .style(new StyleBuilder(workbook)
                        .font(new FontBuilder(workbook)
                                .fontName()
                                .fontSize(fontDefaultSize)
                                .fontWeight(true)
                                .buildFont())
                        .verticalAlign(VerticalAlignment.CENTER)
                        .horizontalAlign(HorizontalAlignment.RIGHT)
                        .buildStyle())

                .cell(Cols.SIXTH_COL.getCol(), CellType.FORMULA)
                .cellFormula(lastRowDevice, lastRowMaterial, lastRowWork, tax)
                .style(new StyleBuilder(workbook)
                        .font(new FontBuilder(workbook)
                                .fontName()
                                .fontSize(fontDefaultSize)
                                .fontWeight(true)
                                .buildFont())
                        .verticalAlign(VerticalAlignment.CENTER)
                        .horizontalAlign(HorizontalAlignment.CENTER)
                        .dataFormat((short) 0x27)
                        .buildStyle())

                // total estimate
                .mergedRegion(lastRowWork + 2, Cols.FIFTH_COL)
                .row(lastRowWork + 2)
                .heightRow(HeightCell.HEIGHT_CELL_DEFAULT.getIndex())
                .cell()
                .value("ИТОГО ОБЩАЯ СУММА: ")
                .style(new StyleBuilder(workbook)
                        .font(new FontBuilder(workbook)
                                .fontName()
                                .fontSize(fontDefaultSize)
                                .fontWeight(true)
                                .buildFont())
                        .verticalAlign(VerticalAlignment.CENTER)
                        .horizontalAlign(HorizontalAlignment.RIGHT)
                        .buildStyle())

                .cell(Cols.SIXTH_COL.getCol(), CellType.FORMULA)
                .cellFormula(lastRowDevice, lastRowMaterial, lastRowWork)
                .style(new StyleBuilder(workbook)
                        .font(new FontBuilder(workbook)
                                .fontName()
                                .fontSize(fontDefaultSize)
                                .fontWeight(true)
                                .buildFont())
                        .verticalAlign(VerticalAlignment.CENTER)
                        .horizontalAlign(HorizontalAlignment.CENTER)
                        .dataFormat((short) 0x27)
                        .buildStyle());



        String fileName = estimate.getAddress() + " " +
                estimate.getCustomer() + " " +
                estimate.getExtId() + " " +
                keyEstimate + ".xlsx";




        try (FileOutputStream outFile = new FileOutputStream(getFile(fileName))) {
            workbook.write(outFile);
        } catch (IOException e) {
            log.error("IN createFile - create estimate {} : error {}", e.getMessage(), e.getStackTrace());
        }
    }

    private CellStyle getStyle() {

        return new StyleBuilder(workbook)
                .font(new FontBuilder(workbook)
                        .fontName()
                        .fontSize(fontDefaultSize)
                        .buildFont())
                .fillPattern(FillPatternType.SOLID_FOREGROUND)
                .color(IndexedColors.GREY_25_PERCENT)
                .verticalAlign(VerticalAlignment.CENTER)
                .horizontalAlign(HorizontalAlignment.CENTER)
                .buildStyle();
    }


    private CellStyle getStyle(int fontSize) {

        return new StyleBuilder(workbook)
                .font(new FontBuilder(workbook)
                        .fontName()
                        .fontSize(fontSize)
                        .buildFont())
                .verticalAlign(VerticalAlignment.CENTER)
                .horizontalAlign(HorizontalAlignment.CENTER)
                .buildStyle();
    }


    private void sectionHeader(int row, String value) {
        new CellBuilder(sheet)
                .mergedRegion(row, Cols.SIXTH_COL)
                .row(row)
                .heightRow(HeightCell.HEIGHT_CELL_SECTION.getIndex())
                .cell()
                .value(value)
                .style(new StyleBuilder(workbook)
                        .font(new FontBuilder(workbook)
                                .fontName()
                                .fontSize(fontDefaultSize)
                                .fontWeight(true)
                                .buildFont())
                        .verticalAlign(VerticalAlignment.BOTTOM)
                        .horizontalAlign(HorizontalAlignment.LEFT)
                        .buildStyle());

    }


    private void headerTable(int row) {

        new CellBuilder(sheet)
                .row(row)
                .heightRow(HeightCell.HEIGHT_CELL_DEFAULT.getIndex())
                .cell(Cols.FIRST_COL.getCol())
                .value("#")
                .style(getStyle())

                .cell(Cols.SECOND_COL.getCol())
                .value("Наименование")
                .style(getStyle())

                .cell(Cols.THIRD_COL.getCol())
                .value("Ед. изм.")
                .style(getStyle())

                .cell(Cols.FOURTH_COL.getCol())
                .value("Кол-во")
                .style(getStyle())

                .cell(Cols.FIFTH_COL.getCol())
                .value("Цена")
                .style(getStyle())

                .cell(Cols.SIXTH_COL.getCol())
                .value("Сумма")
                .style(getStyle());

    }



    private void totalSum(int startRow, int lastRowComponent, String value) {
        new CellBuilder(sheet)
                .mergedRegion(lastRowComponent, Cols.FIFTH_COL)
                .row(lastRowComponent)
                .heightRow(HeightCell.HEIGHT_CELL_DEFAULT.getIndex())
                .cell()
                .value(value)
                .style(new StyleBuilder(workbook)
                        .font(new FontBuilder(workbook)
                                .fontName()
                                .fontSize(fontDefaultSize)
                                .fontWeight(true)
                                .buildFont())
                        .verticalAlign(VerticalAlignment.CENTER)
                        .horizontalAlign(HorizontalAlignment.RIGHT)
                        .buildStyle())

                .cell(Cols.SIXTH_COL.getCol(), CellType.FORMULA)
                .cellFormula(startRow + 1, lastRowComponent)
                .style(new StyleBuilder(workbook)
                        .font(new FontBuilder(workbook)
                                .fontName()
                                .fontSize(fontDefaultSize)
                                .fontWeight(true)
                                .buildFont())
                        .verticalAlign(VerticalAlignment.CENTER)
                        .horizontalAlign(HorizontalAlignment.CENTER)
                        .dataFormat((short) 0x27)
                        .buildStyle());

    }


    private File getDir() {
        // проверяем существует ли папка
        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
            log.info("Dir '{}' is created", uploadDir);
        }

        return uploadDir;
    }



    private File getFile(String fileName) throws IOException {

        String validFileName = getValidFileName(fileName);

        log.info("Save file in valid file name '{}'", validFileName);

        return new File(getDir() + File.separator + validFileName);
    }


    private String getValidFileName(String fileName) {

        String newFileName = fileName.replace("^\\.+", "").replaceAll("[\\\\/:*%,?\"<>|]", " ");

        if(newFileName.length() == 0)
            throw new IllegalStateException("File Name " + fileName + " results in a empty fileName!");

        return newFileName;
    }

}
