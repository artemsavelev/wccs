package com.smart.wccs.service.filecreator.components;


public enum Rows {

    FIRST_ROW(0),
    SECOND_ROW(1),
    THIRD_ROW(2),
    FOURTH_ROW(3),
    FIFTH_ROW(5),
    SIXTH_ROW(6),
    SEVENTH_ROW(7),
    EIGHTH_ROW(8),
    NINTH_ROW(9),
    TENTH_ROW(10),
    ELEVENTH_ROW(11);

    private final int row;


    Rows(int row) {
        this.row = row;
    }


    public int getRow() {
        return row;
    }


}
