package com.austral.ingsis.value;

import com.austral.ingsis.Type;

import java.util.Optional;

public abstract class Value {

    private final Boolean isConstant;
    private final Boolean isDefined;
    private final Type type;

    protected Value(Boolean isConstant, Boolean isDefined, Type type) {
        this.isConstant = isConstant;
        this.isDefined = isDefined;
        this.type = type;
    }

    public Boolean isConstant() {
        return isConstant;
    }

    public Boolean getDefined() {
        return isDefined;
    }

    public Type getType() {
        return type;
    }

    public abstract String toString();
}
