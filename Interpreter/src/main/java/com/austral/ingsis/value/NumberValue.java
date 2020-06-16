package com.austral.ingsis.value;

public class NumberValue extends Value {

    private final Number value;

    public NumberValue(Boolean isConstant, Number value) {
        super(isConstant, true, Type.NUMBER);
        this.value = value;
    }

    public Number getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (value.intValue() == value.doubleValue()){
            return value.intValue() + "";
        }else return value.toString();
    }
}
