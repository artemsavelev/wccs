package com.smart.wccs.service.filecreator.components;

import com.smart.wccs.model.Components;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

public class Data {

    private final Workbook workbook;
    private final Sheet sheet;

    public Data(Workbook workbook, Sheet sheet) {
        this.workbook = workbook;
        this.sheet = sheet;
    }

    public void getData(int row, List<Components> list) {

        if (list.size() == 0) {
            list.add(emptyComponent());
        }

        int i = 0;
        for (Components comp: list) {

            row++;
            i++;

            new CellBuilder(sheet)
                    .row(row)
                    .heightRow(HeightCell.HEIGHT_CELL_DEFAULT.getIndex())
                    .cell()
                    .value(i)
                    .style(getStyle())

                    .cell(ColsTest.SECOND_COL.getCol())
                    .value(comp.getName())
                    .style(getStyle())

                    .cell(ColsTest.THIRD_COL.getCol())
                    .value(comp.getDimension())
                    .style(getStyle())

                    .cell(ColsTest.FOURTH_COL.getCol(), CellType.NUMERIC)
                    .value(comp.getQuantity())
                    .style(getStyle())

                    .cell(ColsTest.FIFTH_COL.getCol(), CellType.NUMERIC)
                    .value(comp.getPrice())
                    .style(new StyleBuilder(workbook)
                            .font(new FontBuilder(workbook)
                                    .fontName()
                                    .fontSize(8)
                                    .buildFont())
                            .verticalAlign(VerticalAlignment.CENTER)
                            .horizontalAlign(HorizontalAlignment.CENTER)
                            .border(BorderStyle.HAIR)
                            .borderColor(IndexedColors.GREY_25_PERCENT)
                            .dataFormat((short) 0x27)
                            .buildStyle())

                    .cell(ColsTest.SIXTH_COL.getCol(), CellType.FORMULA)
                    .cellFormula(row + 1)
                    .style(new StyleBuilder(workbook)
                            .font(new FontBuilder(workbook)
                                    .fontName()
                                    .fontSize(8)
                                    .buildFont())
                            .verticalAlign(VerticalAlignment.CENTER)
                            .horizontalAlign(HorizontalAlignment.CENTER)
                            .border(BorderStyle.HAIR)
                            .borderColor(IndexedColors.GREY_25_PERCENT)
                            .dataFormat((short) 0x27)
                            .buildStyle());

        }

    }

    private CellStyle getStyle() {

        return new StyleBuilder(workbook)
                .font(new FontBuilder(workbook)
                        .fontName()
                        .fontSize(8)
                        .buildFont())
                .verticalAlign(VerticalAlignment.CENTER)
                .horizontalAlign(HorizontalAlignment.CENTER)
                .border(BorderStyle.HAIR)
                .borderColor(IndexedColors.GREY_25_PERCENT)
                .wrapText(true)
                .buildStyle();
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
