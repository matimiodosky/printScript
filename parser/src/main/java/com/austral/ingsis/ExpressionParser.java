package com.austral.ingsis;

import java.util.List;
import java.util.Optional;

public interface ExpressionParser {
    Optional<? extends Expression> parseExpression(List<Token> tokens);
}
