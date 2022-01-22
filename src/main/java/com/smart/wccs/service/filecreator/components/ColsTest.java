package com.smart.wccs.service.filecreator.components;

public enum ColsTest {

    FIRST_COL(0),
    SECOND_COL(1),
    THIRD_COL(2),
    FOURTH_COL(3),
    FIFTH_COL(4),
    SIXTH_COL(5),
    SEVENTH_COL(6),
    EIGHTH_COL(7);

    private final int col;

    ColsTest(int col) {
        this.col = col;
    }

    public int getCol() {
        return col;
    }
}
