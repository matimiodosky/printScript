package com.austral.ingsis.scope.helpers.calculator;

import com.austral.ingsis.value.NumberValue;
import com.austral.ingsis.value.StringValue;
import com.austral.ingsis.value.Value;

public class CalculatorImpl implements Calculator {
    @Override
    public Value calculate(NumberValue left, NumberValue right, String operator) {
        return switch (operator) {
            case "PLUS" -> new NumberValue(true, left.getValue().doubleValue() + right.getValue().doubleValue());
            case "MINUS" -> new NumberValue(true, left.getValue().doubleValue() - right.getValue().doubleValue());
            case "MULT" -> new NumberValue(true, left.getValue().doubleValue() * right.getValue().doubleValue());
            case "DIV" -> new NumberValue(true, left.getValue().doubleValue() / right.getValue().doubleValue());
            default -> throw new RuntimeException("Invalid operator: " + operator);
        };
    }

    @Override
    public Value calculate(NumberValue left, StringValue right, String operator) {
        if (!operator.equals("PLUS")) throw new RuntimeException("Invalid operation");
        String number = left.getValue().doubleValue() == left.getValue().intValue() ? left.getValue().intValue() + "" : left.getValue().toString();
        return new StringValue(true, number + right.getValue());
    }

    @Override
    public Value calculate(StringValue left, NumberValue right, String operator) {
        String number = right.getValue().doubleValue() == right.getValue().intValue() ? right.getValue().intValue() + "" : right.getValue().toString();
        return new StringValue(true, left.getValue() + number);

    }

    @Override
    public Value calculate(StringValue left, StringValue right, String operator) {
        return new StringValue(true, left.getValue() + right.getValue());

    }


}
