package com.smart.wccs.service.filecreator.components;

public enum Rows {

    FIRST_ROW(0),
    SECOND_ROW(1),
    THIRD_ROW(2),
    FOURTH_ROW(3),
    FIFTH_ROW(4),
    SIXTH_ROW(5),
    SEVENTH_ROW(6),
    EIGHTH_ROW(7),
    NINTH_ROW(8),
    TENTH_ROW(9),
    ELEVENTH_ROW(10),
    TWELFTH_ROW(11);

    private final int row;

    Rows(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }


}
