package com.smart.wccs.service.filecreator;

import org.springframework.stereotype.Component;

@Component
public class ExcelFileFactory implements FileFactory {
    @Override
    public FileCreator creator() {
        return new ExcelDocument();
    }
}
