package com.austral.ingsis.matchers.expression;

import com.austral.ingsis.Token;
import com.austral.ingsis.TokenType;
import com.austral.ingsis.expression.Expression;
import com.austral.ingsis.expression.LiteralNumber;
import com.austral.ingsis.matchers.ExpressionMatcher;

import java.util.Optional;
import java.util.stream.Stream;

public class LiteralNumberMatcher extends ExpressionMatcher<LiteralNumber> {

    @Override
    public Optional<LiteralNumber> match(Stream<Token> tokenStream) {
        return tokenStream
                .filter(super::usefulToken)
                .findFirst()
                .filter(token -> token.type == TokenType.NUMBER)
                .flatMap(super::asNumber)
                .map(LiteralNumber::new);
    }
}
