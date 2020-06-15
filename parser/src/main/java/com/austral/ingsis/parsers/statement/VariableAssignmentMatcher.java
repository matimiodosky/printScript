package com.austral.ingsis.parsers.statement;

import com.austral.ingsis.*;
import com.austral.ingsis.expression.Expression;
import com.austral.ingsis.statements.VariableAssignment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VariableAssignmentMatcher extends StatementMatcher<VariableAssignment> {

    public VariableAssignmentMatcher(ExpressionParser parser) {
        super(parser);
    }

    @Override
    public Optional<VariableAssignment> match(Stream<Token> tokens) {
        List<Token> usefulTokens = tokens
                .filter(super::usefulToken)
                .collect(Collectors.toList());

        if (usefulTokens.size() < 4) return Optional.empty();

        Optional<Token> identifier = Optional
                .of(usefulTokens.get(0))
                .filter(token -> token.type == TokenType.IDENTIFIER);

        Optional<Token> equals = Optional
                .of(usefulTokens.get(1))
                .filter(token -> token.type == TokenType.ASSIGNATION);

        Optional<Expression> expression = parser.parseExpression(usefulTokens.subList(2, usefulTokens.size() - 2));

        Optional<Token> semicolon = Optional
                .of(usefulTokens.get(3))
                .filter(token -> token.type == TokenType.SEMICOLON);

        if (identifier.isPresent() && equals.isPresent() && expression.isPresent() && semicolon.isPresent()) {
           return Optional.of(new VariableAssignment(identifier.get().data, expression.get()));
        } else return Optional.empty();

    }

}
