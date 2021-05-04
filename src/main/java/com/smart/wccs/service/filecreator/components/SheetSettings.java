package com.smart.wccs.service.filecreator.components;

import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Sheet;

public class SheetSettings {
    private final Sheet sheet;

    public SheetSettings(Sheet sheet) {
        this.sheet = sheet;
    }

    public void getSettings() {
        this.sheet.setFitToPage(true);
        this.sheet.setDisplayGridlines(false);
        this.sheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
        this.sheet.setColumnWidth(0, 1000);
        this.sheet.setColumnWidth(1, 15000);
        this.sheet.setColumnWidth(2, 3000);
        this.sheet.setColumnWidth(3, 3500);
        this.sheet.setColumnWidth(4, 3500);
        this.sheet.setColumnWidth(5, 3500);
    }
}
