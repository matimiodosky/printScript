package com.austral.ingsis.matchers.statement;

import com.austral.ingsis.*;
import com.austral.ingsis.Expression;
import com.austral.ingsis.matchers.StatementMatcher;
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
                .filter(token -> token.getType() == TokenType.IDENTIFIER);

        Optional<Token> equals = Optional
                .of(usefulTokens.get(1))
                .filter(token -> token.getType() == TokenType.ASSIGNATION);

        Expression expression = parser.parseExpression(usefulTokens.subList(2, usefulTokens.size() - 1));

        Optional<Token> semicolon = Optional
                .of(usefulTokens.get(3))
                .filter(token -> token.getType() == TokenType.SEMICOLON);

        if (identifier.isPresent() && equals.isPresent() && semicolon.isPresent()) {
           return Optional.of(new VariableAssignment(identifier.get().getData(), expression));
        } else return Optional.empty();

    }

}
