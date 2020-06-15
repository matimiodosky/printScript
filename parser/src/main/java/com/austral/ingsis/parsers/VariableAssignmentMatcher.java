package com.austral.ingsis.parsers;

import com.austral.ingsis.Token;
import com.austral.ingsis.TokenType;
import com.austral.ingsis.statements.VariableAssignment;
import com.austral.ingsis.statements.VariableDefinition;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VariableAssignmentMatcher extends StatementMatcher<VariableAssignment<?>> {

    @Override
    public Optional<VariableAssignment<?>> match(Stream<Token> tokens) {
        List<Token> usefulTokens = tokens
                .filter(super::usefulToken)
                .collect(Collectors.toList());

        if (usefulTokens.size() < 4)return Optional.empty();

        Optional<Token> identifier = Optional
                .of(usefulTokens.get(0))
                .filter(token -> token.type == TokenType.IDENTIFIER);
        Optional<Token> equals = Optional
                .of(usefulTokens.get(1))
                .filter(token -> token.type == TokenType.ASSIGNATION);
        Optional<Token> literalValue = Optional
                .of(usefulTokens.get(2))
                .filter(token -> token.type == TokenType.LITERAL || token.type == TokenType.NUMBER);
        Optional<Token> semicolon = Optional
                .of(usefulTokens.get(3))
                .filter(token -> token.type == TokenType.SEMICOLON);

        if (identifier.isPresent() && equals.isPresent() && literalValue.isPresent() && semicolon.isPresent()) {
            if (asNumber(literalValue.get()).isPresent()) {
                return Optional.of(
                        new VariableAssignment<>(
                                usefulTokens,
                                identifier.get().data,
                                asNumber(literalValue.get()).get())
                );
            } else {
                return Optional.of(
                        new VariableAssignment<>(
                                usefulTokens,
                                identifier.get().data,
                                literalValue.get().data
                        )
                );
            }
        } else return Optional.empty();

    }

}
