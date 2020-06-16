package com.austral.ingsis.scope.helpers.resolver;

import com.austral.ingsis.Expression;
import com.austral.ingsis.expression.*;
import com.austral.ingsis.scope.Scope;
import com.austral.ingsis.scope.helpers.calculator.Calculator;
import com.austral.ingsis.scope.helpers.calculator.CalculatorImpl;
import com.austral.ingsis.value.*;

public class ResolverImpl implements Resolver {


    private final Calculator calculator = new CalculatorImpl();
    private final Scope scope;

    public ResolverImpl(Scope scope) {
        this.scope = scope;
    }

    @Override
    public Value resolve(Expression expression, Boolean isConstant) {
        if (expression instanceof LiteralString literalString) {
            return new StringValue(isConstant, literalString.getValue());
        }
        if (expression instanceof LiteralNumber literalNumber) {
            return new NumberValue(isConstant, literalNumber.getValue());
        }
        if (expression instanceof LiteralBoolean literalBoolean) {
            return new BooleanValue(isConstant, literalBoolean.getValue());
        }
        if (expression instanceof Identifier identifier) {
            return scope.getVariableValue(identifier.getName());
        }
        if (expression instanceof Operation operation) {
            return resolveOperation(operation);
        } else throw new RuntimeException("Not implemented: " + expression);
    }

    private Value resolveOperation(Operation operation) {
        Value left = resolve(operation.getLeft());
        Value right = resolve(operation.getRight());

        if (left.isNumber() && right.isNumber()) {
            return calculator.calculate(left.getAsNumber(), right.getAsNumber(), operation.getOperator());
        } else if (left.isNumber() && right.isString()) {
            return calculator.calculate(left.getAsNumber(), right.getAsString(), operation.getOperator());

        } else if (left.isString() && right.isNumber()) {
            return calculator.calculate(left.getAsString(), right.getAsNumber(), operation.getOperator());

        } else if (left.isString() && right.isString()) {
            return calculator.calculate(left.getAsString(), right.getAsString(), operation.getOperator());
        } else throw new RuntimeException("Invalid operation");
    }

    @Override
    public Value resolve(Expression expression) {
        return resolve(expression, true);
    }
}
