package com.austral.ingsis.value;

import java.util.Optional;

public class StringValue extends Value {

    private final String value;

    public StringValue(String value, Boolean isConstant) {
        super(isConstant);
        this.value = value;
    }

    @Override
    public Optional<String> getAsString() {
        return Optional.of(value);
    }
}
