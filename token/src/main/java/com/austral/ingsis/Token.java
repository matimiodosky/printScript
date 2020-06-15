package com.austral.ingsis;

public class Token {

    private final TokenType type;
    private final String data;
    private final int line;
    private final int index;

    public Token(TokenType type, String data, int line, int index) {
        this.type = type;
        this.data = data;
        this.line = line;
        this.index = index;
    }

    public TokenType getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public int getLine() {
        return line;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return String.format("(%s %s)", type.name(), data);
    }
}
