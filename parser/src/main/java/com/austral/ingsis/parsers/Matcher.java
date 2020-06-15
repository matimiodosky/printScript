package com.austral.ingsis.parsers;

import com.austral.ingsis.Token;
import com.austral.ingsis.TokenType;

import java.util.Optional;

public abstract class Matcher {

    public boolean usefulToken(Token token) {
        return token.type != TokenType.WHITESPACE &&
                token.type != TokenType.NEWLINE;
    }

    protected Optional<Number> asNumber(Token token) {
        if (token.type == TokenType.NUMBER) {
            try {
                return Optional.of(Double.parseDouble(token.data));
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

}
