package com.austral.ingsis.value;

import java.util.Optional;

public class StringValue extends Value {

    private final String value;

    public StringValue(Boolean isConstant, String value) {
        super(isConstant, true, Type.STRING);
        this.value = value;
    }

    @Override
    public Optional<StringValue> getAsString() {
        return Optional.of(this);
    }

    @Override
    public boolean isString() {
        return true;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
