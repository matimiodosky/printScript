package com.austral.ingsis;

import com.austral.ingsis.expression.Expression;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ExpressionParser {
    Optional<Expression> parseExpression(List<Token> tokens);
}
