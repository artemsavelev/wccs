package com.smart.wccs.service.filecreator.components;

import com.smart.wccs.model.Components;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

public class Data {

    private final Workbook workbook;
    private final Sheet sheet;
    private final Font font;
    private final CellStyle style;

    public Data(Workbook workbook, Sheet sheet, Font font, CellStyle style) {
        this.workbook = workbook;
        this.sheet = sheet;
        this.font = font;
        this.style = style;
    }



    public int getData(int rowNum, List<Components> list) {
        Row row;
        Cell cell;

        CellStyle styleLocal = workbook.createCellStyle();
        styleLocal.setWrapText(true);
        styleLocal.setFont(font);
        styleLocal.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        styleLocal.setVerticalAlignment(VerticalAlignment.CENTER);
        styleLocal.setBorderBottom(BorderStyle.HAIR);
        styleLocal.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        if (list.size() == 0) {
            list.add(emptyComponent());
        }

        int i = 0;
        for (Components comp: list) {

            rowNum++;
            i++;
            row = sheet.createRow(rowNum);
//            row.setHeightInPoints(-1);
            // # (A)
            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue(i);
            cell.setCellStyle(styleLocal);
            // name (B)
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(comp.getName());
            cell.setCellStyle(styleLocal);
            // dimension (C)
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(comp.getDimension());
            cell.setCellStyle(styleLocal);
            // quantity (D)
            cell = row.createCell(3, CellType.NUMERIC);
            cell.setCellValue(comp.getQuantity());
            cell.setCellStyle(styleLocal);
            // price (E)
            cell = row.createCell(4, CellType.NUMERIC);
            cell.setCellValue(comp.getPrice());
            style.setBorderBottom(BorderStyle.HAIR);
            style.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style.setDataFormat((short) 0x27);
            cell.setCellStyle(style);
            // sum (F)
            String formula = "E" + (rowNum + 1) + "*D" + (rowNum + 1);
            cell = row.createCell(5, CellType.FORMULA);
            cell.setCellFormula(formula);
            style.setBorderBottom(BorderStyle.HAIR);
            style.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style.setDataFormat((short) 0x27);
            cell.setCellStyle(style);



        }
        return rowNum;
    }

    private Components emptyComponent() {
        return new Components() {
            @Override
            public String getName() {
                return "";
            }

            @Override
            public void setName(String name) {

            }

            @Override
            public String getDimension() {
                return "";
            }

            @Override
            public void setDimension(String dimension) {

            }

            @Override
            public double getPrice() {
                return 0;
            }

            @Override
            public void setPrice(double price) {

            }

            @Override
            public int getQuantity() {
                return 0;
            }

            @Override
            public void setQuantity(int quantity) {

            }

            @Override
            public String getNote() {
                return "";
            }

            @Override
            public void setNote(String note) {

            }
        };
    }

}