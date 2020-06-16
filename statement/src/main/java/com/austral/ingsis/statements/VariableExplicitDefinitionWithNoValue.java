package com.austral.ingsis.statements;


import com.austral.ingsis.Statement;

/**
 * (const | let) (identifier) (equals) (literal | number | expression) (semicolon)
 */
public class VariableExplicitDefinitionWithNoValue extends Statement {

    private final String identifier;
    private final Boolean isConstant;
    private final String type;

    public VariableExplicitDefinitionWithNoValue(String identifier, Boolean isConstant, String type, int line, int index) {
        super(line, index);
        this.identifier = identifier;
        this.isConstant = isConstant;
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Boolean getConstant() {
        return isConstant;
    }

    public String getType() {
        return type;
    }
}
