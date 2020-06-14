package com.austral.ingsis.impl;

public class SyntaxError extends RuntimeException   {

    final int line;
    public SyntaxError(int line) {
        super(String.format("At line %d", line));
        this.line = line;
    }

    public int getLine() {
        return line;
    }
}
