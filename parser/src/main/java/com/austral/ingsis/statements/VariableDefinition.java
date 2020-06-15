package com.austral.ingsis.statements;


import com.austral.ingsis.Token;
import com.austral.ingsis.expression.Expression;

import java.util.List;
import java.util.stream.Stream;

/**
 * (const | let) (identifier) (equals) (literal | number | expression) (semicolon)
 */
public class VariableDefinition<T> extends Statement {

    String identifier;
    Boolean isConstant;
    Expression value;

    public VariableDefinition(String identifier, Boolean isConstant, Expression value) {
        this.identifier = identifier;
        this.isConstant = isConstant;
        this.value = value;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Boolean getConstant() {
        return isConstant;
    }

    public Expression getValue() {
        return value;
    }
}
