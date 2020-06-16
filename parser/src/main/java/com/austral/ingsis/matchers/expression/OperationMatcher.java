package com.austral.ingsis.matchers.expression;

import com.austral.ingsis.Expression;
import com.austral.ingsis.ExpressionParser;
import com.austral.ingsis.Token;
import com.austral.ingsis.TokenType;
import com.austral.ingsis.expression.Identifier;
import com.austral.ingsis.expression.Operation;
import com.austral.ingsis.matchers.ExpressionMatcher;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OperationMatcher extends ExpressionMatcher<Operation> {


    public OperationMatcher(ExpressionParser expressionMatcher) {
        super(expressionMatcher);
    }

    @Override
    public Optional<Operation> match(Stream<Token> tokenStream) {

        List<Token> tokens = tokenStream
                .filter(super::usefulToken)
                .collect(Collectors.toList());

        List<TokenType> tokensTypes = tokens
                .stream()
                .filter(super::usefulToken)
                .map(Token::getType)
                .collect(Collectors.toList());



        int plus = tokensTypes.indexOf(TokenType.PLUS);
        int minus = tokensTypes.indexOf(TokenType.MINUS);
        int operator = -1;
        if (plus != -1)operator =plus;
        if (minus != -1) operator = minus;
        if (operator == -1) return Optional.empty();

        Optional<? extends Expression> left = expressionParser.parseExpression(tokens.subList(0, operator));
        Optional<? extends Expression> right = expressionParser.parseExpression(tokens.subList(operator + 1, tokens.size()));

        if (left.isPresent() && right.isPresent()){
            return Optional.of(new Operation(left.get(), right.get(), tokensTypes.get(operator).name()));
        }
        return Optional.empty();
    }
}
