package com.austral.ingsis.value;

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

    public NumberValue getAsNumber() {
        return ((NumberValue) this);
    }

    public boolean isNumber(){return type.equals(Type.NUMBER);}

    public StringValue getAsString() {
        return ((StringValue) this);
    }

    public boolean isString(){return type.equals(Type.STRING);}


    public BooleanValue getAsBoolean() {
        return ((BooleanValue) this);
    }

    public boolean isBoolean(){return type.equals(Type.BOOLEAN);}


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
