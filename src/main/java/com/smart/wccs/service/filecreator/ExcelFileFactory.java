package com.smart.wccs.service.filecreator;

public class ExcelFileFactory implements FileFactory {
    @Override
    public FileCreator creator() {
        return new ExcelDocument();
    }
}
