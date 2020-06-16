package com.austral.ingsis.value;

public class StringValue extends Value {

    private final String value;

    public StringValue(Boolean isConstant, String value) {
        super(isConstant, true, Type.STRING);
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
