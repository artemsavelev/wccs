package com.smart.wccs.service.filecreator.components;

import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Sheet;

public class SheetSettings {
    private final Sheet sheet;

    public SheetSettings(Sheet sheet) {
        this.sheet = sheet;
    }

    public void getSettings() {
        sheet.setFitToPage(true);
        sheet.setDisplayGridlines(false);
        sheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
        sheet.setColumnWidth(0, 1000);
        sheet.setColumnWidth(1, 15000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 3500);
        sheet.setColumnWidth(4, 3500);
        sheet.setColumnWidth(5, 3500);
    }
}
