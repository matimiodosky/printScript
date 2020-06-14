package com.austral.ingsis.impl;

public class SyntaxError extends RuntimeException   {

    final int line;
    final int index;
    public SyntaxError(int line, int index) {
        super(String.format("At  [%d:%d]", line, index));
        System.out.println(super.getMessage());
        this.line = line;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public int getLine() {
        return line;
    }
}
