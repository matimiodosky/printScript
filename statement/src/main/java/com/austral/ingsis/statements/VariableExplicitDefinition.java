package com.austral.ingsis.statements;


import com.austral.ingsis.Expression;
import com.austral.ingsis.Statement;

/**
 * (const | let) (identifier) (equals) (literal | number | expression) (semicolon)
 */
public class VariableExplicitDefinition extends Statement {

    private final String identifier;
    private final Boolean isConstant;
    private final Expression value;
    private final String type;

    public VariableExplicitDefinition(String identifier, Boolean isConstant, Expression value, String type, int line, int index) {
        super(line, index);
        this.identifier = identifier;
        this.isConstant = isConstant;
        this.value = value;
        this.type = type;
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

    public String getType() {
        return type;
    }
}
