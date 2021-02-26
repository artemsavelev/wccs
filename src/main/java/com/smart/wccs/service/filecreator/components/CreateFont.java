package com.smart.wccs.service.filecreator.components;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

public class CreateFont {
    private final Workbook workbook;

    public CreateFont(Workbook workbook) {
        this.workbook = workbook;
    }

    public Font getFont(int fontSize, boolean fontWeight) {
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) fontSize);
        font.setBold(fontWeight);
        font.setFontName("Montserrat");
        return font;
    }

    public Font getFont(IndexedColors color, int fontSize, boolean fontWeight) {
        Font font = getFont(fontSize, fontWeight);
        font.setColor(color.getIndex());
        return font;
    }


}
