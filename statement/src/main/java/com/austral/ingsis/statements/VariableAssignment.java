package com.austral.ingsis.statements;


import com.austral.ingsis.Expression;
import com.austral.ingsis.Statement;

/**
 * (const | let) (identifier) (equals) (literal | number | expression) (semicolon)
 */
public class VariableAssignment extends Statement {

    private final String identifier;
    private final Expression expression;



    public VariableAssignment(String identifier, Expression value, int line, int index) {
        super(line, index);
        this.identifier = identifier;
        this.expression = value;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Expression getExpression() {
        return expression;
    }
}
