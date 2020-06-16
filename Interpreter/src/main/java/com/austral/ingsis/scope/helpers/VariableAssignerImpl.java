package com.austral.ingsis.scope.helpers;

import com.austral.ingsis.statements.VariableAssignment;
import com.austral.ingsis.value.Value;

public class VariableAssignerImpl implements VariableAssigner {

    private final VariableAssignment statement;
    private final Resolver resolver;

    private VariableAssignerImpl(VariableAssignment statement, Resolver resolver) {
        this.statement = statement;
        this.resolver = resolver;
    }

    public VariableAssignerImpl() {
        this.statement = null;
        this.resolver = null;
    }

    @Override
    public VariableAssigner withStatement(VariableAssignment statement) {
       return new VariableAssignerImpl(statement, resolver);
    }

    @Override
    public VariableAssigner withResolver(Resolver resolver) {
        return new VariableAssignerImpl(statement, resolver);
    }

    @Override
    public Value apply(String s, Value oldValue) {
        if (resolver == null) throw new IllegalStateException("Unspecified resolver");
        if (statement == null) throw new IllegalStateException("Unspecified statement");
        if (oldValue.isConstant()) throw new RuntimeException("Re assignment to const variable");
        Value value = resolver.resolve(statement.getExpression());
        if (value.getType() != oldValue.getType()) throw new RuntimeException("Incompatible types");
        return value;
    }


}
