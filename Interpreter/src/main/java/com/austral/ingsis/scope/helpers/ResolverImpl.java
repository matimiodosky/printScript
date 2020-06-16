package com.austral.ingsis.scope.helpers;

import com.austral.ingsis.Expression;
import com.austral.ingsis.expression.Identifier;
import com.austral.ingsis.expression.LiteralString;
import com.austral.ingsis.scope.Scope;
import com.austral.ingsis.value.StringValue;
import com.austral.ingsis.value.Value;

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
        if (expression instanceof Identifier identifier) {
            return scope.getVariableValue(identifier.getName());
        } else throw new RuntimeException("Not implemented");
    }

    @Override
    public Value resolve(Expression expression) {
        return resolve(expression, true);
    }
}
