package com.smart.wccs.service.filecreator.components;

import org.apache.poi.ss.usermodel.*;

public class CreateStyle {

    private final Workbook workbook;
    private final Font font;


    public CreateStyle(Workbook workbook, Font font) {
        this.workbook = workbook;
        this.font = font;
    }

    public CellStyle getStyle() {
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        style.setFont(font);
        return style;
    }

    public CellStyle getStyle(HorizontalAlignment hAlign, VerticalAlignment vAlign) {
        CellStyle style = getStyle();
        style.setAlignment(hAlign);
        style.setVerticalAlignment(vAlign);
        return style;
    }

    public CellStyle getStyle(boolean fontBold, HorizontalAlignment hAlign, VerticalAlignment vAlign) {
        CellStyle style = getStyle(hAlign, vAlign);
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 8);
        font.setFontName("Montserrat");
        font.setBold(fontBold);
        style.setFont(font);
        return style;
    }

    public CellStyle getStyle(IndexedColors color,  HorizontalAlignment hAlign, VerticalAlignment vAlign) {
        CellStyle style = getStyle(hAlign, vAlign);
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 8);
        font.setFontName("Montserrat");
        font.setColor(color.getIndex());
        style.setFont(font);
        return style;
    }

    public CellStyle getStyle(IndexedColors color, FillPatternType fill, HorizontalAlignment hAlign, VerticalAlignment vAlign) {
        CellStyle style = getStyle(hAlign, vAlign);
        style.setFillForegroundColor(color.getIndex());
        style.setFillPattern(fill);
        return style;
    }



}
