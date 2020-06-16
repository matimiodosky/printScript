package com.austral.ingsis.value;

public class Null extends Value {
    public Null(Type type) {
        super(true, false, type);
    }

    @Override
    public String toString() {
        return "null";
    }
}
