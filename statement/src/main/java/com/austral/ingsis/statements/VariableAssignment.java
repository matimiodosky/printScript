package com.austral.ingsis.statements;


import com.austral.ingsis.Expression;
import com.austral.ingsis.Statement;

/**
 * (const | let) (identifier) (equals) (literal | number | expression) (semicolon)
 */
public class VariableAssignment extends Statement {

    private final String identifier;
    private final Expression<?> value;



    public VariableAssignment(String identifier, Expression<?> value, int line, int index) {
        super(line, index);
        this.identifier = identifier;
        this.value = value;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Expression<?> getValue() {
        return value;
    }
}
