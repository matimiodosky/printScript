package com.austral.ingsis.expression;

import com.austral.ingsis.Expression;

public class LiteralString extends Expression {


    private final String value;


    public LiteralString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
