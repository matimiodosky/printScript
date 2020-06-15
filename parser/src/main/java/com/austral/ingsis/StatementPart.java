package com.austral.ingsis;

public class StatementPart {

    private final TokenType[] types;
    private final int from;
    private final int to;

    public StatementPart(TokenType[] types, int from, int to) {
        this.types = types;
        this.from = from;
        this.to = to;
    }

    public TokenType[] getTypes() {
        return types;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}
