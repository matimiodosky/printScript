package com.austral.ingsis.value;

public class Null extends Value {

    public Null(Type type, Boolean isConstant) {
        super(isConstant, false, type);
    }

    @Override
    public String toString() {
        return "null";
    }
}
