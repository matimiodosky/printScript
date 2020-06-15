package com.austral.ingsis.statements;


import com.austral.ingsis.Token;

import java.util.List;

/**
 * (const | let) (identifier) (equals) (literal | number | expression) (semicolon)
 */
public class VariableAssignment<T> extends Statement {

    String identifier;
    T value;

    public VariableAssignment(List<Token> tokens, String identifier, T value) {
        super(tokens);
        this.identifier = identifier;
        this.value = value;
    }

    public String getIdentifier() {
        return identifier;
    }

    public T getValue() {
        return value;
    }
}
