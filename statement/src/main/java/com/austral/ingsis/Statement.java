package com.austral.ingsis;

public abstract class Statement {

    private final int line;
    private final int index;


    protected Statement(int line, int index) {
        this.line = line;
        this.index = index;
    }

    public int getLine() {
        return line;
    }

    public int getIndex() {
        return index;
    }
}
