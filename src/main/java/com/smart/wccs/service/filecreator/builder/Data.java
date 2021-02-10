package com.smart.wccs.service.filecreator.builder;

import com.smart.wccs.model.Device;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

public class Data {
    public static void getData(Sheet sheet, CellStyle style, int rowNum, List<Device> list) {
//        List<Employee> list = EmployeeDAO.listEmployees();
//        List<Device> list = estimate.getDevices();

        // Data
        for (Device emp : list) {
            rowNum++;
            Row row = sheet.createRow(rowNum);

            // # (A)
            row.createCell(0, CellType.STRING).setCellValue(emp.getId());
            row.setRowStyle(style);
            // name (B)
            row.createCell(1, CellType.STRING).setCellValue(emp.getName());
            row.setRowStyle(style);
            // dimension (C)
            row.createCell(2, CellType.STRING).setCellValue(emp.getDimension());
            row.setRowStyle(style);
            // quantity (D)
            row.createCell(3, CellType.NUMERIC).setCellValue(emp.getQuantity());
            row.setRowStyle(style);

            // price (E)
            row.createCell(4, CellType.BOOLEAN).setCellValue(emp.getPrice());
            row.setRowStyle(style);
            // sum (F)
//            row.createCell(5, CellType.BOOLEAN).setCellValue(emp.getSum());
//            row.setRowStyle(style);



        }
        sheet.addMergedRegion(new CellRangeAddress(rowNum + 1, rowNum + 1, 0, 4));
    }

}
