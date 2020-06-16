package com.austral.ingsis.statements;


import com.austral.ingsis.Statement;
import com.austral.ingsis.Expression;

/**
 * (const | let) (identifier) (equals) (literal | number | expression) (semicolon)
 */
public class VariableDefinition extends Statement {

    final String identifier;
    final Boolean isConstant;
    final Expression<?> value;

    public VariableDefinition(String identifier, Boolean isConstant, Expression<?> value, int line, int index) {
        super(line, index);
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

    public Expression<?> getValue() {
        return value;
    }
}
