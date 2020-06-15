package com.austral.ingsis;

public abstract class Expression<T> {

    private final T value;

    public Expression(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
