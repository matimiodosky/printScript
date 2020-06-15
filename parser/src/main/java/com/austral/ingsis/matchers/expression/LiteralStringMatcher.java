package com.austral.ingsis.matchers.expression;

import com.austral.ingsis.Token;
import com.austral.ingsis.TokenType;
import com.austral.ingsis.expression.LiteralNumber;
import com.austral.ingsis.expression.LiteralString;
import com.austral.ingsis.matchers.ExpressionMatcher;

import java.util.Optional;
import java.util.stream.Stream;

public class LiteralStringMatcher extends ExpressionMatcher<LiteralString> {

    @Override
    public Optional<LiteralString> match(Stream<Token> tokenStream) {
        return tokenStream
                .filter(super::usefulToken)
                .findFirst()
                .filter(token -> token.getType() == TokenType.LITERAL)
                .map(Token::getData)
                .map(LiteralString::new);
    }
}
