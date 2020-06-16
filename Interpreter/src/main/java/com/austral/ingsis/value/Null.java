package com.austral.ingsis.value;

import com.austral.ingsis.Type;

public class Null extends Value {
    public Null(Type type) {
        super(true, false, type);
    }

    @Override
    public String toString() {
        return "null";
    }
}
