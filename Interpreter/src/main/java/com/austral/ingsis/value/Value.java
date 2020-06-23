package com.austral.ingsis.value;

import java.util.Optional;

public abstract class Value {

    private final Boolean isConstant;
    private final Boolean isDefined;
    private final Type type;

    protected Value(Boolean isConstant, Boolean isDefined, Type type) {
        this.isConstant = isConstant;
        this.isDefined = isDefined;
        this.type = type;
    }

    public Value plus(Value value){
        if(this.isString() || value.isString()){
            return new StringValue(true, this.toString() + value.toString());
        }
        throw new RuntimeException("Invalid operation: " + this.getType().name() + " + " + value.getType().name());
    }

    public Value minus(Value value){
        throw new RuntimeException("Invalid operation: " + this.getType().name() + " + " + value.getType().name());
    }

    public Value divided(Value value){
        throw new RuntimeException("Invalid operation: " + this.getType().name() + " + " + value.getType().name());
    }

    public Value multiplied(Value value){
        throw new RuntimeException("Invalid operation: " + this.getType().name() + " + " + value.getType().name());
    }

    public Value grater(Value value){
        throw new RuntimeException("Invalid operation: " + this.getType().name() + " + " + value.getType().name());
    }

    public Value less(Value value){
        throw new RuntimeException("Invalid operation: " + this.getType().name() + " + " + value.getType().name());
    }

    public Value equals(Value value){
        throw new RuntimeException("Invalid operation: " + this.getType().name() + " + " + value.getType().name());
    }

    public Optional<NumberValue> getAsNumber() {
        return Optional.empty();
    }

    public boolean isNumber() {
        return false;
    }

    public Optional<StringValue> getAsString() {
        return Optional.empty();
    }

    public boolean isString() {
        return type.equals(Type.STRING);
    }

    public Optional<BooleanValue> getAsBoolean() {
        return Optional.empty();
    }

    public boolean isBoolean() {
        return type.equals(Type.BOOLEAN);
    }

    public Boolean isConstant() {
        return isConstant;
    }

    public Boolean getDefined() {
        return isDefined;
    }

    public Type getType() {
        return type;
    }

    public abstract String toString();
}
