package com.smart.wccs.service.filecreator.components;

public enum Cols {

    FIRST_COL(0),
    SECOND_COL(1),
    THIRD_COL(2),
    FOURTH_COL(3),
    FIFTH_COL(4),
    SIXTH_COL(5),
    SEVENTH_COL(6),
    EIGHTH_COL(7);

//    LAST_COL(5),
//    LAST_COL_TOTAL(4),
//    LAST_COL_TAX(4);

    private final int col;


    Cols(int col) {
        this.col = col;
    }

    public int getCol() {
        return col;
    }
}
