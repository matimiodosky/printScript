package com.austral.ingsis.expression;

import com.austral.ingsis.Expression;

public class Identifier extends Expression<Object> {

    private final String name;

    public Identifier(String name) {
        super(null);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
