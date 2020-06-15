package com.austral.ingsis.parsers;

import com.austral.ingsis.SyntaxError;
import com.austral.ingsis.Token;
import com.austral.ingsis.TokenType;
import com.austral.ingsis.statements.Assignment;
import com.austral.ingsis.statements.Statement;

import javax.swing.text.html.HTMLDocument;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * (const | let) (identifier) (equals) (literal | number | expression) (semicolon)
 */
public class AssignmentMatcher extends StatementMatcher<Assignment<?>> {


    @Override
    public Optional<Assignment<?>> match(Statement statement) {
        List<Token> usefulTokens = statement
                .getTokens()
                .stream()
                .filter(token -> token.type != TokenType.WHITESPACE)
                .filter(token -> token.type != TokenType.NEWLINE)
                .collect(Collectors.toList());

        Optional<Token> keyWord = Optional
                .of(usefulTokens.get(0))
                .filter(token -> token.type == TokenType.LET || token.type == TokenType.CONST);
        Optional<Token> identifier = Optional
                .of(usefulTokens.get(1))
                .filter(token -> token.type == TokenType.IDENTIFIER);

        Optional<Token> equals = Optional
                .of(usefulTokens.get(2))
                .filter(token -> token.type == TokenType.ASSIGNATION);

        Optional<Token> value = Optional
                .of(usefulTokens.get(3))
                .filter(token -> token.type == TokenType.LITERAL || token.type == TokenType.NUMBER);

        Optional<Token> semicolon = Optional
                .of(usefulTokens.get(4))
                .filter(token -> token.type == TokenType.SEMICOLON);

        if (keyWord.isPresent() && identifier.isPresent() && equals.isPresent() && value.isPresent() && semicolon.isPresent()) {
            if (asNumber(value.get()).isPresent()) {
                return Optional.of(new Assignment<>(
                        statement.getTokens(),
                        identifier.get().data,
                        identifier.get().type == TokenType.CONST,
                        asNumber(value.get()).get())
                );
            } else {
                return Optional.of(new Assignment<>(
                                statement.getTokens(),
                                identifier.get().data,
                                identifier.get().type == TokenType.CONST,
                                value.get()
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
