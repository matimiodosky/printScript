package com.austral.ingsis.statements;


import com.austral.ingsis.Token;

import java.util.List;

/**
 * (const | let) (identifier) (equals) (literal | number | expression) (semicolon)
 */
public class Assignment<T> extends Statement {

    String identifier;
    Boolean isConstant;
    T value;

    public Assignment(List<Token> tokens, String identifier, Boolean isConstant, T value) {
        super(tokens);
        this.identifier = identifier;
        this.isConstant = isConstant;
        this.value = value;
    }
}
