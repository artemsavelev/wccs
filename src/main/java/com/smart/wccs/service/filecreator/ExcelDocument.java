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

    // font size
    private final int fontDefaultSize = 8;

    private int fontTitleSize = 12;

    private Workbook workbook;

    private Sheet sheet;

    private Font font;


    @Override
    public void createFile(Estimate estimate) {



        if ("ПРЕДВАРИТЕЛЬНАЯ".equals(estimate.getKey())) {
            keyEstimate = "ПРЕДВАРИТЕЛЬНАЯ";
        } else {
            keyEstimate = "ФАКТИЧЕСКАЯ";
        }

        workbook =  new XSSFWorkbook();

        sheet = workbook.createSheet("Смета " + keyEstimate);

        font = new CreateFont(workbook).getFont(fontDefaultSize, false);

        List<Components> listDevice = new ArrayList<>(estimate.getDevices());
        List<Components> listMaterial = new ArrayList<>(estimate.getMaterials());
        List<Components> listWork = new ArrayList<>(estimate.getWorks());



        new SheetSettings(sheet).getSettings();


        // первая строка
        new CreateCell(sheet, CellType.STRING).getCell(
                new CreateStyle(workbook).getStyle(
                        new CreateFont(workbook).getFont(fontTitleSize, false),
                        HorizontalAlignment.CENTER,
                        VerticalAlignment.CENTER
                ),
                Rows.FIRST_ROW.getRow(),
                Cols.FIRST_COL.getCol(),
                Cols.SIXTH_COL.getCol(),
                HeightCell.HEIGHT_CELL_TITLE.getIndex(),
                "ФАКТИЧЕСКАЯ".equals(keyEstimate) ? "Смета" : "Смета " + keyEstimate
        );

        // вторая строка
        new CreateCell(sheet, CellType.STRING).getCell(
                new CreateStyle(workbook, font).getStyle(HorizontalAlignment.CENTER, VerticalAlignment.CENTER),
                Rows.SECOND_ROW.getRow(),
                Cols.FIRST_COL.getCol(),
                Cols.SIXTH_COL.getCol(),
                HeightCell.HEIGHT_CELL_DEFAULT.getIndex(),
                "№: " + estimate.getExtId()
        );

        // третья строка
        new CreateCell(sheet, CellType.STRING).getCell(
                new CreateStyle(workbook, font).getStyle(HorizontalAlignment.CENTER, VerticalAlignment.CENTER),
                Rows.THIRD_ROW.getRow(),
                Cols.FIRST_COL.getCol(),
                Cols.SIXTH_COL.getCol(),
                HeightCell.HEIGHT_CELL_DEFAULT.getIndex(),
                "адрес: " + estimate.getAddress()
        );

        // четвертая строка
        new CreateCell(sheet, CellType.STRING).getCell(
                new CreateStyle(workbook, font).getStyle(HorizontalAlignment.CENTER, VerticalAlignment.CENTER),
                Rows.FOURTH_ROW.getRow(),
                Cols.FIRST_COL.getCol(),
                Cols.SIXTH_COL.getCol(),
                HeightCell.HEIGHT_CELL_DEFAULT.getIndex(),
                "заказчик: " + estimate.getCustomer()
        );

        // пятая строка
        new CreateCell(sheet, CellType.STRING).getCell(
                new CreateStyle(workbook, font).getStyle(HorizontalAlignment.CENTER, VerticalAlignment.CENTER),
                Rows.FIFTH_ROW.getRow(),
                Cols.FIRST_COL.getCol(),
                Cols.SIXTH_COL.getCol(),
                HeightCell.HEIGHT_CELL_DEFAULT.getIndex(),
                "составил: " +
                        estimate.getAuthor().getLastName() + " " + estimate.getAuthor().getFirstName() + " " +
                        estimate.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );

        // шестая строка
        cellEmpty(Rows.SIXTH_ROW.getRow(), HeightCell.HEIGHT_CELL_DEFAULT.getIndex());


        // седьмая строка
        new CreateCell(sheet, CellType.STRING).getCell(
                new CreateStyle(workbook, font).getStyle(HorizontalAlignment.LEFT, VerticalAlignment.CENTER),
                Rows.SEVENTH_ROW.getRow(),
                Cols.FIRST_COL.getCol(),
                Cols.SIXTH_COL.getCol(),
                HeightCell.HEIGHT_CELL_DEFAULT.getIndex(),
                "Описание работ :"
        );

        // восьмая строка
        new CreateCell(sheet, CellType.STRING).getCell(
                new CreateStyle(workbook, font).getStyle(
                        HorizontalAlignment.LEFT,
                        VerticalAlignment.TOP
                ),
                Rows.EIGHTH_ROW.getRow(),
                Cols.FIRST_COL.getCol(),
                Cols.SIXTH_COL.getCol(),
                HeightCell.HEIGHT_CELL_DESCRIPTION.getIndex(),
                estimate.getWorkDescription()
        );

        // девятая строка
        new CreateCell(sheet, CellType.STRING).getCell(
                new CreateStyle(workbook).getStyle(
                        new CreateFont(workbook).getFont(
                                IndexedColors.RED,
                                fontDefaultSize,
                                false
                        ),
                        HorizontalAlignment.LEFT,
                        VerticalAlignment.CENTER
                ),
                Rows.NINTH_ROW.getRow(),
                Cols.FIRST_COL.getCol(),
                Cols.SIXTH_COL.getCol(),
                HeightCell.HEIGHT_CELL_TEXT.getIndex(),
                estimate.getSimpleText()
        );




        // секция (Активное оборудование)
        nameSection(Rows.TENTH_ROW.getRow(), "1. Активное оборудование");

        headerTable(Rows.TENTH_ROW.getRow());

        // Data device
        int rowDevice = new Data(workbook, sheet, font).getData(Rows.ELEVENTH_ROW.getRow(), listDevice);

        //
        int firstRowDevice = listDevice.size() + Rows.ELEVENTH_ROW.getRow() + 1;
        int lastRowDevice = firstRowDevice + 1;
        int nextRowDevice = lastRowDevice + 1;
        int firstRowFormulaDevice = firstRowDevice - listDevice.size() + 1;
        int lastRowFormulaDevice = firstRowDevice;

        // sub total device
        totalName(firstRowDevice, "ИТОГО за активное оборудование: ");

        totalPrice(firstRowFormulaDevice, lastRowFormulaDevice);
        // конец секции (Активное оборудование)




        nameSection(lastRowDevice, "2. Материалы и оборудование");

        // header table
        headerTable(lastRowDevice);

        // Data material
        int rowMaterial = new Data(workbook, sheet, font).getData(nextRowDevice, listMaterial);

        //
        int firstRowMaterial = rowMaterial + 1;
        int lastRowMaterial = rowMaterial + 2;
        int nextRowMaterial = rowMaterial + 3;
        int firstRowFormulaMaterial = rowMaterial - listMaterial.size() + 2;
        int lastRowFormulaMaterial = rowMaterial + 1;

        // sub total material

        totalName(firstRowMaterial, "ИТОГО за материалы и оборудование: ");

        totalPrice(firstRowFormulaMaterial, lastRowFormulaMaterial);




        nameSection(lastRowMaterial, "3. Работы");

        // header table
        headerTable(lastRowMaterial);

        // Data work
        int rowWork = new Data(workbook, sheet, font).getData(nextRowMaterial, listWork);

        //
        int firstRowWork = rowWork + 1;
        int lastRowWork = rowWork + 2;
        int nextRowWork = rowWork + 3;
        int firstRowFormulaWork = rowWork - listWork.size() + 2;
        int lastRowFormulaWork = rowWork + 1;

        // sub total work
        totalName(firstRowWork, "ИТОГО за работы: ");

        totalPrice(firstRowFormulaWork, lastRowFormulaWork);










        cellEmpty(lastRowWork, HeightCell.HEIGHT_CELL_EMPTY.getIndex());


        // total tax
        totalName(nextRowWork, "В том числе НДС " + tax + "%: ");

        new CreateCell(sheet, CellType.STRING).cellFormula(
                new CreateStyle(workbook).getStyle(
                        new CreateFont(workbook).getFont(
                                fontDefaultSize,
                                true
                        ),
                        HorizontalAlignment.CENTER,
                        VerticalAlignment.CENTER
                ),
                rowDevice + 2,
                rowMaterial + 2,
                rowWork + 2, tax,
                rowWork + 3,
                HeightCell.HEIGHT_CELL_DEFAULT.getIndex()
        );

        // total estimate
        totalName(rowWork + 4, "ИТОГО ОБЩАЯ СУММА: ");

        new CreateCell(sheet, CellType.STRING).cellFormula(
                new CreateStyle(workbook).getStyle(
                        new CreateFont(workbook).getFont(
                                fontDefaultSize,
                                true
                        ),
                        HorizontalAlignment.CENTER,
                        VerticalAlignment.CENTER
                ),
                rowDevice + 2,
                rowMaterial + 2,
                rowWork + 2,
                rowWork + 4,
                HeightCell.HEIGHT_CELL_DEFAULT.getIndex()
        );





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


    private void nameSection(int row, String name) {
        new CreateCell(sheet, CellType.STRING).getCell(
                new CreateStyle(workbook).getStyle(
                        new CreateFont(workbook).getFont(
                                fontDefaultSize,
                                true
                        ),
                        HorizontalAlignment.LEFT,
                        VerticalAlignment.BOTTOM
                ),
                row,
                Cols.FIRST_COL.getCol(),
                Cols.SIXTH_COL.getCol(),
                HeightCell.HEIGHT_CELL_SECTION.getIndex(),
                name
        );
    }


    private void headerTable(int row) {
        new CreateHeaderTable(sheet, CellType.STRING).getHeader(
                new CreateStyle(workbook, font).getStyle(
                        IndexedColors.GREY_25_PERCENT,
                        FillPatternType.SOLID_FOREGROUND,
                        HorizontalAlignment.CENTER,
                        VerticalAlignment.CENTER
                ),
                row
        );
    }


    private void totalName(int row, String name) {
        new CreateCell(sheet, CellType.STRING).getCell(
                new CreateStyle(workbook).getStyle(
                        new CreateFont(workbook).getFont(
                                fontDefaultSize,
                                true
                        ),
                        HorizontalAlignment.RIGHT,
                        VerticalAlignment.CENTER
                ),
                row,
                Cols.FIRST_COL.getCol(),
                Cols.FIFTH_COL.getCol(),
                HeightCell.HEIGHT_CELL_DEFAULT.getIndex(),
                name
        );
    }


    private void totalPrice(int startRow, int endRow) {
        new CreateCell(sheet, CellType.STRING).cellFormula(
                new CreateStyle(workbook).getStyle(
                        new CreateFont(workbook).getFont(
                                fontDefaultSize,
                                true
                        ),
                        HorizontalAlignment.CENTER,
                        VerticalAlignment.CENTER
                ),
                startRow,
                endRow,
                HeightCell.HEIGHT_CELL_DEFAULT.getIndex()
        );
    }


    private void cellEmpty(int row, int heightCell) {
        new CreateCell(sheet, CellType.STRING).getCell(
                new CreateStyle(workbook, font).getStyle(),
                row,
                Cols.FIRST_COL.getCol(),
                Cols.FIFTH_COL.getCol(),
                heightCell,
                ""
        );
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

        String newFileName = fileName.replace("^\\.+", "").replaceAll("[\\\\/:*?\"<>|]", "-");

        if(newFileName.length() == 0)
            throw new IllegalStateException("File Name " + fileName + " results in a empty fileName!");

        return newFileName;
    }

}
