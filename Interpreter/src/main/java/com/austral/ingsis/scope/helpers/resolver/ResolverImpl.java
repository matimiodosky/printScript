package com.austral.ingsis.scope.helpers.resolver;

import com.austral.ingsis.Expression;
import com.austral.ingsis.expression.*;
import com.austral.ingsis.scope.Scope;
import com.austral.ingsis.value.*;

import java.util.Optional;

public class ResolverImpl implements Resolver {


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
        switch (operation.getOperator()) {
            case "PLUS":
                return left.plus(right);
            case "MINUS":
                return left.minus(right);
            case "MULT":
                return left.multiplied(right);
            case "DIV":
                return left.divided(right);
            case "GRATEREQUAL": {
                Optional<BooleanValue> grater = left.grater(right).getAsBoolean();
                Optional<BooleanValue> equal = left.equals(right).getAsBoolean();
                return new BooleanValue(true,
                        grater.isPresent() &&
                                equal.isPresent() &&
                                (grater.get().getValue() || equal.get().getValue())
                );
            }
            case "GRATER":
                return left.grater(right);
            case "LESSEQUAL":
                Optional<BooleanValue> grater = left.less(right).getAsBoolean();
                Optional<BooleanValue> equal = left.equals(right).getAsBoolean();
                return new BooleanValue(true,
                        grater.isPresent() &&
                                equal.isPresent() &&
                                (grater.get().getValue() || equal.get().getValue())
                );
            case "LESS":
                return left.less(right);
            default: throw new RuntimeException("Invalid operator: " + operation.getOperator());
        }
    }

    @Override
    public Value resolve(Expression expression) {
        return resolve(expression, true);
    }
}
