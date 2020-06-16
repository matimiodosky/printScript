package com.austral.ingsis.matchers.statement;

import com.austral.ingsis.Expression;
import com.austral.ingsis.ExpressionParser;
import com.austral.ingsis.Token;
import com.austral.ingsis.TokenType;
import com.austral.ingsis.matchers.StatementMatcher;
import com.austral.ingsis.statements.VariableExplicitDefinition;
import com.austral.ingsis.statements.VariableExplicitDefinitionWithNoValue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * (const | let) (identifier) (equals) (literal | number | expression) (semicolon)
 */
public class VariableExplicitDefinitionWithNoValueMatcher extends StatementMatcher<VariableExplicitDefinitionWithNoValue> {


    public VariableExplicitDefinitionWithNoValueMatcher(ExpressionParser parser) {
        super(parser);
    }

    @Override
    public Optional<VariableExplicitDefinitionWithNoValue> match(Stream<Token> tokens) {
        List<Token> usefulTokens = tokens
                .filter(super::usefulToken)
                .collect(Collectors.toList());

        if (usefulTokens.size() < 5) return Optional.empty();

        Optional<Token> keyWord = Optional
                .of(usefulTokens.get(0))
                .filter(token -> token.getType() == TokenType.LET || token.getType() == TokenType.CONST);

        Optional<Token> identifier = Optional
                .of(usefulTokens.get(1))
                .filter(token -> token.getType() == TokenType.IDENTIFIER);

        Optional<Token> color = Optional
                .of(usefulTokens.get(2))
                .filter(token -> token.getType() == TokenType.COLON);

        Optional<Token> type = Optional
                .of(usefulTokens.get(3))
                .filter(token -> token.getType() == TokenType.STRINGTYPE || token.getType() == TokenType.NUMBERTYPE || token.getType() == TokenType.BOOLEANTYPE);

        Optional<Token> semicolon = Optional
                .of(usefulTokens.get(4))
                .filter(token -> token.getType() == TokenType.SEMICOLON);

        if (keyWord.isPresent() && identifier.isPresent() && semicolon.isPresent() && type.isPresent()) {
            return Optional.of(
                    new VariableExplicitDefinitionWithNoValue(
                            identifier.get().getData(),
                            keyWord.get().getType() == TokenType.CONST,
                            type.get().getData(),
                            semicolon.get().getLine(),
                            semicolon.get().getIndex()
                    )
            );
        } else return Optional.empty();

    }
}
