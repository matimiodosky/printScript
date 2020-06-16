package com.austral.ingsis.expression;

import com.austral.ingsis.Expression;

public class Identifier extends Expression {

    private final String name;

    public Identifier(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
