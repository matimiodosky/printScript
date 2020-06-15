package com.austral.ingsis.expression;

import com.austral.ingsis.Expression;

public class LiteralBoolean extends Expression {

    private final String value;

    public LiteralBoolean(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
