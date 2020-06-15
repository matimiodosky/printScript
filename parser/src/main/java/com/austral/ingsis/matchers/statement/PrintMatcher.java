package com.austral.ingsis.matchers.statement;

import com.austral.ingsis.Expression;
import com.austral.ingsis.ExpressionParser;
import com.austral.ingsis.Token;
import com.austral.ingsis.TokenType;
import com.austral.ingsis.matchers.StatementMatcher;
import com.austral.ingsis.statements.Print;
import com.austral.ingsis.statements.VariableExplicitDefinition;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * (const | let) (identifier) (equals) (literal | number | expression) (semicolon)
 */
public class PrintMatcher extends StatementMatcher<Print> {


    public PrintMatcher(ExpressionParser parser) {
        super(parser);
    }

    @Override
    public Optional<Print> match(Stream<Token> tokens) {
        List<Token> usefulTokens = tokens
                .filter(super::usefulToken)
                .collect(Collectors.toList());

        if (usefulTokens.size() < 4)return Optional.empty();

        Optional<Token> keyWord = Optional
                .of(usefulTokens.get(0))
                .filter(token -> token.getType() == TokenType.PRINT);

        Optional<Token> open = Optional
                .of(usefulTokens.get(1))
                .filter(token -> token.getType() == TokenType.OPENPAR);

        Optional<? extends Expression> expression = parser.parseExpression(usefulTokens.subList(2, usefulTokens.size() - 2));


        Optional<Token> close = Optional
                .of(usefulTokens.get(usefulTokens.size() - 2))
                .filter(token -> token.getType() == TokenType.CLOSEPAR);

        Optional<Token> semicolon = Optional
                .of(usefulTokens.get(usefulTokens.size() - 1))
                .filter(token -> token.getType() == TokenType.SEMICOLON);

        if (expression.isPresent() && keyWord.isPresent() && open.isPresent() && close.isPresent() && semicolon.isPresent()) {
            return Optional.of(
                    new Print(expression.get())
            );
        } else return Optional.empty();

    }
}
