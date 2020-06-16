package com.austral.ingsis.value;

public class NumberValue extends Value {

    private final Number value;

    public NumberValue(Boolean isConstant, Number value) {
        super(isConstant, true, Type.NUMBER);
        this.value = value;
    }

    @Override
    public String toString() {
        return value.intValue() + "";
    }
}
