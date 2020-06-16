package com.austral.ingsis.scope.helpers.variable;

import com.austral.ingsis.Expression;
import com.austral.ingsis.Statement;
import com.austral.ingsis.scope.helpers.resolver.Resolver;
import com.austral.ingsis.value.Type;
import com.austral.ingsis.statements.VariableDefinition;
import com.austral.ingsis.statements.VariableExplicitDefinition;
import com.austral.ingsis.statements.VariableExplicitDefinitionWithNoValue;
import com.austral.ingsis.value.Null;
import com.austral.ingsis.value.Value;

public class VariableDefinerImpl implements VariableDefiner {

    private final Statement statement;
    private final boolean isConstant;
    private final boolean explicitType;
    private final boolean hasExpression;
    private final Type type;
    private final Expression expression;
    private final Resolver resolver;

    private VariableDefinerImpl(Statement statement, Boolean isConstant, Boolean explicitType, Boolean hasExpression, Type type, Expression expression, Resolver resolver) {
        this.statement = statement;
        this.isConstant = isConstant;
        this.explicitType = explicitType;
        this.hasExpression = hasExpression;
        this.type = type;
        this.expression = expression;
        this.resolver = resolver;
    }

    public VariableDefinerImpl() {
        this.statement = null;
        this.isConstant = false;
        this.explicitType = false;
        this.hasExpression = false;
        this.type = null;
        this.expression = null;
        this.resolver = null;
    }


    public VariableDefinerImpl withStatement(VariableExplicitDefinition statement) {
        return new VariableDefinerImpl(
                statement,
                statement.getConstant(),
                true,
                true,
                Type.valueOf(statement.getType().toUpperCase()),
                statement.getExpression(),
                resolver
        );
    }

    public VariableDefinerImpl withStatement(VariableDefinition statement) {
        return new VariableDefinerImpl(
                statement,
                statement.getConstant(),
                false,
                true,
                null,
                statement.getExpression(),
                resolver
        );
    }

    public VariableDefinerImpl withStatement(VariableExplicitDefinitionWithNoValue statement) {
        return new VariableDefinerImpl(
                statement,
                statement.getConstant(),
                true,
                false,
                Type.valueOf(statement.getType().toUpperCase()),
                null,
                resolver
        );
    }

    public VariableDefinerImpl withResolver(Resolver resolver) {
        return new VariableDefinerImpl(
                statement,
                isConstant,
                explicitType,
                hasExpression,
                type,
                expression,
                resolver
        );
    }

    @Override
    public Value apply(String s, Value oldValue) {
        if (resolver == null) throw new IllegalStateException("Unspecified resolver");
        if (statement == null) throw new IllegalStateException("Unspecified statement");

        if (oldValue != null)
            throw new RuntimeException("Variable already defined in scope at [" + statement.getLine() + "," + statement.getIndex() + "]");

        Value value;
        if (hasExpression) {
            value = resolver.resolve(expression, isConstant);
        } else {
           value = new Null(explicitType ? type : null, isConstant);
        }
        if (explicitType){
            if(value.getType() != type) throw new RuntimeException("Incompatible types at [" + statement.getLine() + "," + statement.getIndex() + "]");
        }
        return value;
    }

}
