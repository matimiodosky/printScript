package com.austral.ingsis.parsers;

import com.austral.ingsis.Token;
import com.austral.ingsis.TokenType;
import com.austral.ingsis.statements.Statement;

import java.util.Optional;
import java.util.stream.Stream;

public abstract class StatementMatcher<T extends Statement> {

    public abstract Optional<T> match(Stream<Token> tokens);

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
