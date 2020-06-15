package com.austral.ingsis.expression;

import com.austral.ingsis.Expression;

public class Identifier extends Expression {

    private final String value;

    public Identifier(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
