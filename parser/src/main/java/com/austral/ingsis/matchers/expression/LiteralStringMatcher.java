package com.austral.ingsis.matchers.expression;

import com.austral.ingsis.Token;
import com.austral.ingsis.TokenType;
import com.austral.ingsis.expression.LiteralNumber;
import com.austral.ingsis.expression.LiteralString;
import com.austral.ingsis.matchers.ExpressionMatcher;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LiteralStringMatcher extends ExpressionMatcher<LiteralString> {

    @Override
    public Optional<LiteralString> match(Stream<Token> tokenStream) {
        List<Token> usefulTokens = tokenStream
                .filter(super::usefulToken)
                .collect(Collectors.toList());

        if (usefulTokens.size() > 1) return Optional.empty();

        return usefulTokens
                .stream()
                .findFirst()
                .filter(token -> token.getType() == TokenType.LITERAL)
                .map(Token::getData)
                .map(LiteralString::new);
    }
}
