package com.smart.wccs.service.filecreator.components;

public enum HeightCell {

    HEIGHT_CELL_DEFAULT(15),
    HEIGHT_CELL_TITLE(30),
    HEIGHT_CELL_SECTION(25),
    HEIGHT_CELL_DESCRIPTION(100),
    HEIGHT_CELL_TEXT(70),
    HEIGHT_CELL_EMPTY(7);


    private final int index;

    HeightCell(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
