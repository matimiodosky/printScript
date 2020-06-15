package com.austral.ingsis.matchers.statement;

import com.austral.ingsis.*;
import com.austral.ingsis.Expression;
import com.austral.ingsis.matchers.StatementMatcher;
import com.austral.ingsis.statements.VariableDefinition;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * (const | let) (identifier) (equals) (literal | number | expression) (semicolon)
 */
public class VariableDefinitionMatcher extends StatementMatcher<VariableDefinition<?>> {


    public VariableDefinitionMatcher(ExpressionParser parser) {
        super(parser);
    }

    @Override
    public Optional<VariableDefinition<?>> match(Stream<Token> tokens) {
        List<Token> usefulTokens = tokens
                .filter(super::usefulToken)
                .collect(Collectors.toList());

        if (usefulTokens.size() < 5)return Optional.empty();

        Optional<Token> keyWord = Optional
                .of(usefulTokens.get(0))
                .filter(token -> token.getType() == TokenType.LET || token.getType() == TokenType.CONST);

        Optional<Token> identifier = Optional
                .of(usefulTokens.get(1))
                .filter(token -> token.getType() == TokenType.IDENTIFIER);

        Optional<Token> equals = Optional
                .of(usefulTokens.get(2))
                .filter(token -> token.getType() == TokenType.ASSIGNATION);

        Expression expression = parser.parseExpression(usefulTokens.subList(3, usefulTokens.size() - 1));

        Optional<Token> semicolon = Optional
                .of(usefulTokens.get(4))
                .filter(token -> token.getType() == TokenType.SEMICOLON);

        if (keyWord.isPresent() && identifier.isPresent() && equals.isPresent() && semicolon.isPresent()) {
            return Optional.of(new VariableDefinition<>(identifier.get().getData(), keyWord.get().getType() == TokenType.CONST, expression));
        } else return Optional.empty();

    }
}