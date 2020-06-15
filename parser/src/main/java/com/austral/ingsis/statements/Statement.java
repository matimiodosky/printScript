package com.austral.ingsis.statements;

import com.austral.ingsis.Token;

import java.util.List;

public class Statement {
    private final List<Token> tokens;

    public Statement(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Token> getTokens() {
        return tokens;
    }
}
