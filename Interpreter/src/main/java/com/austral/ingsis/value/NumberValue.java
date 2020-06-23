package com.austral.ingsis.value;

import java.util.Optional;

public class NumberValue extends Value {

    private final Number value;

    public NumberValue(Boolean isConstant, Number value) {
        super(isConstant, true, Type.NUMBER);
        this.value = value;
    }

    @Override
    public Optional<NumberValue> getAsNumber() {
        return Optional.of(this);
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public Value plus(Value value) {
        if (value.isNumber() && value.getAsNumber().isPresent()) {
            return new NumberValue(true, this.getValue().doubleValue() + value.getAsNumber().get().getValue().doubleValue());
        }
        return super.plus(value);
    }

    @Override
    public Value minus(Value value) {
        if (value.getAsNumber().isEmpty()) return super.minus(value);
        return new NumberValue(
                true,
                this
                        .getValue()
                        .doubleValue()
                        -
                        value
                                .getAsNumber()
                                .get()
                                .getValue()
                                .doubleValue()
        );
    }

    @Override
    public Value multiplied(Value value) {
        if (value.getAsNumber().isEmpty()) return super.minus(value);
        return new NumberValue(
                true,
                this
                        .getValue()
                        .doubleValue()
                        *
                        value
                                .getAsNumber()
                                .get()
                                .getValue()
                                .doubleValue()
        );
    }

    @Override
    public Value divided(Value value) {
        if (value.getAsNumber().isEmpty()) return super.minus(value);
        return new NumberValue(
                true,
                this
                        .getValue()
                        .doubleValue()
                        /
                        value
                                .getAsNumber()
                                .get()
                                .getValue()
                                .doubleValue()
        );
    }

    @Override
    public Value grater(Value value) {
        if (value.getAsNumber().isEmpty()) return super.minus(value);
        return new BooleanValue(
                true,
                this
                        .getValue()
                        .doubleValue()
                        >
                        value
                                .getAsNumber()
                                .get()
                                .getValue()
                                .doubleValue()
        );
    }

    @Override
    public Value less(Value value) {
        if (value.getAsNumber().isEmpty()) return super.minus(value);
        return new BooleanValue(
                true,
                this
                        .getValue()
                        .doubleValue()
                        <
                        value
                                .getAsNumber()
                                .get()
                                .getValue()
                                .doubleValue()
        );
    }

    @Override
    public Value equals(Value value) {
        if (value.getAsNumber().isEmpty()) return super.minus(value);
        return new BooleanValue(
                true,
                this
                        .getValue()
                        .doubleValue()
                        ==
                        value
                                .getAsNumber()
                                .get()
                                .getValue()
                                .doubleValue()
        );
    }

    public Number getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (value.intValue() == value.doubleValue()) {
            return value.intValue() + "";
        } else return value.toString();
    }
}
