package com.austral.ingsis.exception;

public class ParsignError extends RuntimeException {

    private final int line;
    private final int index;

    public ParsignError(String message, int line, int index) {
        super(message);
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
