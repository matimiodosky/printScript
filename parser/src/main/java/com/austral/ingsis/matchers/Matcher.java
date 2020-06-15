package com.austral.ingsis.matchers;

import com.austral.ingsis.Token;
import com.austral.ingsis.TokenType;

import java.util.Optional;
import java.util.stream.Stream;

public abstract class Matcher<T> {

    public abstract Optional<T> match(Stream<Token> tokens);

    protected boolean usefulToken(Token token) {
        return token.getType() != TokenType.WHITESPACE &&
                token.getType() != TokenType.NEWLINE;
    }

    protected Optional<Number> asNumber(Token token) {
        if (token.getType() == TokenType.NUMBER) {
            try {
                return Optional.of(Double.parseDouble(token.getData()));
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

}
