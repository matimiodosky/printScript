package com.austral.ingsis.expression;

import com.austral.ingsis.Expression;

public class Operation extends Expression {

    private final Expression left;
    private final Expression right;
    private final String operator;

    public Operation(Expression left, Expression right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public String getOperator() {
        return operator;
    }
}
