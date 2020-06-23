package com.austral.ingsis.matchers;

import com.austral.ingsis.Expression;
import com.austral.ingsis.ExpressionParser;
import com.austral.ingsis.Token;
import com.austral.ingsis.TokenType;

import java.util.Optional;
import java.util.stream.Stream;

public abstract class ExpressionMatcher<T extends Expression> {

    public abstract Optional<? extends T> match(Stream<Token> tokens);

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

    protected final ExpressionParser expressionParser;

    protected ExpressionMatcher(ExpressionParser expressionMatcher) {
        this.expressionParser = expressionMatcher;
    }
}
