package com.austral.ingsis.expression;

public class LiteralNumber extends Expression {

    private final Number value;


    public LiteralNumber(Number value) {
        this.value = value;
    }

    public Number getValue() {
        return value;
    }
}
