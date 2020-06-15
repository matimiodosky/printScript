package com.austral.ingsis;

import com.austral.ingsis.expression.Expression;

import java.util.List;
import java.util.Optional;

public interface ExpressionParser {
    Optional<? extends Expression> parseExpression(List<Token> tokens);
}
