package com.austral.ingsis.parsers;

import com.austral.ingsis.Token;
import com.austral.ingsis.TokenType;
import com.austral.ingsis.statements.VariableDefinition;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * (const | let) (identifier) (equals) (literal | number | expression) (semicolon)
 */
public class VariableDefinitionMatcher extends StatementMatcher<VariableDefinition<?>> {


    @Override
    public Optional<VariableDefinition<?>> match(Stream<Token> tokens) {
        List<Token> usefulTokens = tokens
                .filter(super::usefulToken)
                .collect(Collectors.toList());

        if (usefulTokens.size() < 5)return Optional.empty();
        Optional<Token> keyWord = Optional
                .of(usefulTokens.get(0))
                .filter(token -> token.type == TokenType.LET || token.type == TokenType.CONST);
        Optional<Token> identifier = Optional
                .of(usefulTokens.get(1))
                .filter(token -> token.type == TokenType.IDENTIFIER);
        Optional<Token> equals = Optional
                .of(usefulTokens.get(2))
                .filter(token -> token.type == TokenType.ASSIGNATION);
        Optional<Token> literalValue = Optional
                .of(usefulTokens.get(3))
                .filter(token -> token.type == TokenType.LITERAL || token.type == TokenType.NUMBER);
        Optional<Token> semicolon = Optional
                .of(usefulTokens.get(4))
                .filter(token -> token.type == TokenType.SEMICOLON);

        if (keyWord.isPresent() && identifier.isPresent() && equals.isPresent() && literalValue.isPresent() && semicolon.isPresent()) {
            if (asNumber(literalValue.get()).isPresent()) {
                return Optional.of(
                        new VariableDefinition<>(
                                usefulTokens,
                                identifier.get().data,
                                identifier.get().type == TokenType.CONST,
                                asNumber(literalValue.get()).get())
                );
            } else {
                return Optional.of(
                        new VariableDefinition<>(
                                usefulTokens,
                                identifier.get().data,
                                identifier.get().type == TokenType.CONST,
                                literalValue.get()
                        )
                );
            }
        } else return Optional.empty();

    }

    private Optional<Number> asNumber(Token token) {
        if (token.type == TokenType.NUMBER) {
            try {
                return Optional.of(Double.parseDouble(token.data));
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
