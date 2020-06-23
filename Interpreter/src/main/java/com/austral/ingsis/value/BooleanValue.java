package com.austral.ingsis.value;

import java.util.Optional;

public class BooleanValue extends Value {

    public Boolean getValue() {
        return value;
    }

    private final Boolean value;

    @Override
    public Optional<BooleanValue> getAsBoolean() {
        return Optional.of(this);
    }

    @Override
    public boolean isBoolean() {
        return true;
    }

    public BooleanValue(Boolean isConstant, Boolean value) {
        super(isConstant, true, Type.BOOLEAN);
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
