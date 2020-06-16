package com.austral.ingsis.expression;

import com.austral.ingsis.Expression;

public class LiteralBoolean extends Expression {

    private final Boolean value;

    public LiteralBoolean(Boolean value) {
       this.value = value;
    }

    public Boolean getValue() {
        return value;
    }
}
