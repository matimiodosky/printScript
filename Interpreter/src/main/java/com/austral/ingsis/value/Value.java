package com.austral.ingsis.value;

import java.util.Optional;

public abstract class Value {

    private final Boolean isConstant;

    protected Value(Boolean isConstant) {
        this.isConstant = isConstant;
    }

    public abstract Optional<String> getAsString();

    public Boolean getConstant() {
        return isConstant;
    }
}
