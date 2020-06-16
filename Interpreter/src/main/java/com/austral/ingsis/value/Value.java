package com.austral.ingsis.value;

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
