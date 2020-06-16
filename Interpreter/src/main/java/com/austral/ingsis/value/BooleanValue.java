package com.austral.ingsis.value;

public class BooleanValue extends Value {

    private final Boolean value;

    public BooleanValue(Boolean isConstant, Boolean value) {
        super(isConstant, true, Type.BOOLEAN);
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
