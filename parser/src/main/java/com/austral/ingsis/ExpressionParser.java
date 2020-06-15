package com.austral.ingsis;

import java.util.List;

public interface ExpressionParser {
    Expression parseExpression(List<Token> tokens);
}
