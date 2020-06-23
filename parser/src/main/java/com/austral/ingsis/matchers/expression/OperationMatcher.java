package com.austral.ingsis.matchers.expression;

import com.austral.ingsis.Expression;
import com.austral.ingsis.ExpressionParser;
import com.austral.ingsis.Token;
import com.austral.ingsis.TokenType;
import com.austral.ingsis.expression.Identifier;
import com.austral.ingsis.expression.Operation;
import com.austral.ingsis.matchers.ExpressionMatcher;

import java.util.Arrays;
import java.util.Comparator;
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


        Optional<Integer> operator = Stream.of(
                TokenType.PLUS,
                TokenType.MINUS,
                TokenType.MULT,
                TokenType.DIV,
                TokenType.GRATER,
                TokenType.LESSEQUAL,
                TokenType.LESS,
                TokenType.GRATEREQUAL
        )
                .map(tokensTypes::indexOf)
                .filter(i -> i != -1)
                .min(Comparator.naturalOrder());

       if (operator.isEmpty())return Optional.empty();

        Optional<? extends Expression> left = expressionParser.parseExpression(tokens.subList(0, operator.get()));
        Optional<? extends Expression> right = expressionParser.parseExpression(tokens.subList(operator.get() + 1, tokens.size()));

        if (left.isPresent() && right.isPresent()) {
            return Optional.of(new Operation(left.get(), right.get(), tokensTypes.get(operator.get()).name()));
        }
        return Optional.empty();
    }
}
