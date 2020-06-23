package com.austral.ingsis.impl;

public class SyntaxError extends RuntimeException   {

    final int line;
    final int index;
    public SyntaxError(int line, int index) {
        super(String.format("At  [%d:%d]", line, index));
        this.line = line;
        this.index = index;
    }



    public SyntaxError(int line, int index, String token, String message) {
        super(String.format("At  [%d:%d] %s (%s)", line, index, token, message));
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
