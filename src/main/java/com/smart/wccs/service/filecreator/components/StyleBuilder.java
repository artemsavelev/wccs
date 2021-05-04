package com.smart.wccs.service.filecreator.components;

import org.apache.poi.ss.usermodel.*;


public class StyleBuilder {

    private final CellStyle style;

    public StyleBuilder(Workbook workbook) {
        this.style = workbook.createCellStyle();
    }

    public CellStyle buildStyle() {
        return style;
    }

    public StyleBuilder horizontalAlign(HorizontalAlignment hAlign) {
        this.style.setAlignment(hAlign);
        return this;
    }

    public StyleBuilder verticalAlign(VerticalAlignment vAlign) {
        this.style.setVerticalAlignment(vAlign);
        return this;
    }

    public StyleBuilder font(Font font) {
        this.style.setFont(font);
        return this;
    }

    public StyleBuilder color(IndexedColors color) {
        this.style.setFillForegroundColor(color.getIndex());
        return this;
    }

    public StyleBuilder fillPattern(FillPatternType fill) {
        this.style.setFillPattern(fill);
        return this;
    }

    public StyleBuilder dataFormat(short format) {
        this.style.setDataFormat(format);
        return this;
    }

    public StyleBuilder border(BorderStyle style) {
        this.style.setBorderBottom(style);
        return this;
    }

    public StyleBuilder borderColor(IndexedColors colors) {
        this.style.setBottomBorderColor(colors.getIndex());
        return this;
    }

    public StyleBuilder wrapText(boolean wrap) {
        this.style.setWrapText(wrap);
        return this;
    }

}
