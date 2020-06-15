package com.austral.ingsis.statements;


import com.austral.ingsis.Token;

import java.util.List;
import java.util.stream.Stream;

/**
 * (const | let) (identifier) (equals) (literal | number | expression) (semicolon)
 */
public class VariableDefinition<T> extends Statement {

    String identifier;
    Boolean isConstant;
    T value;

    public VariableDefinition(List<Token> tokens, String identifier, Boolean isConstant, T value) {
        super(tokens);
        this.identifier = identifier;
        this.isConstant = isConstant;
        this.value = value;
    }
}
