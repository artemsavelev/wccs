package com.smart.wccs.service.filecreator.components;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;

public class FontBuilder {


    private final Font font;

    @Value("${fontName}")
    private String fontName;

    public FontBuilder(Workbook workbook) {
        this.font = workbook.createFont();
    }

    public Font buildFont() {
        return this.font;
    }

    public FontBuilder fontName() {
        this.font.setFontName(this.fontName);
        return this;
    }

    public FontBuilder fontName(String fontName) {
        this.font.setFontName(fontName);
        return this;
    }

    public FontBuilder fontSize(int fontSize) {
        this.font.setFontHeightInPoints((short) fontSize);
        return this;
    }

    public FontBuilder fontWeight(boolean fontWeight) {
        this.font.setBold(fontWeight);
        return this;
    }

    public FontBuilder fontColor(IndexedColors color) {
        this.font.setColor(color.getIndex());
        return this;
    }


}
