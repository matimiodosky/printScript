package com.austral.ingsis.matchers.statement;

import com.austral.ingsis.*;
import com.austral.ingsis.matchers.StatementMatcher;
import com.austral.ingsis.statements.IfElse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * (const | let) (identifier) (equals) (literal | number | expression) (semicolon)
 */
public class IfElseMatcher extends StatementMatcher<Statement> {


    private final Parser statementParser;

    public IfElseMatcher(ExpressionParser expressionParser, Parser statementParser) {
        super(expressionParser);
        this.statementParser = statementParser;
    }

    @Override
    public Optional<? extends Statement > match(Stream<Token> tokens, Token peek) {
        List<Token> usefulTokens = tokens
                .filter(super::usefulToken)
                .collect(Collectors.toList());
        if (usefulTokens.size() < 9) return Optional.empty();

        List<TokenType> tokenTypes = usefulTokens.stream().map(Token::getType).collect(Collectors.toList());

        Optional<Token> ifKeyword = Optional.of(usefulTokens.get(0)).filter(token -> token.getType().equals(TokenType.IF));

        Optional<Integer> openBraceIndex = indexOf(tokenTypes, TokenType.OPENBRACE);

        Optional<Integer> finalCloseBrace = lastIndexOf(tokenTypes, TokenType.CLOSEBRACE);

        Optional<Integer> elseIndex = indexOf(tokenTypes, TokenType.ELSE);

        if (elseIndex.isEmpty()) {
            return Optional.empty();
        }

        Optional<Integer> secondOpenBraceIndex = indexOf(tokenTypes.subList(elseIndex.get(), tokenTypes.size()), TokenType.OPENBRACE);

        Optional<Integer> secondCloseBrace = indexOf(tokenTypes.subList(elseIndex.get(), tokenTypes.size()), TokenType.CLOSEBRACE);



        if (ifKeyword.isPresent() && openBraceIndex.isPresent() && finalCloseBrace.isPresent() && secondCloseBrace.isPresent() && secondOpenBraceIndex.isPresent()) {
            Optional<? extends Expression> condition = parser.parseExpression(usefulTokens.subList(2, openBraceIndex.get() - 1));
            Stream<Statement> ifStatements = statementParser.parse(usefulTokens.subList(openBraceIndex.get() + 1, elseIndex.get() - 1).stream());
            Stream<Statement> elseStatements = statementParser.parse(usefulTokens.subList(elseIndex.get() + 3, finalCloseBrace.get()).stream());

            if (condition.isEmpty()) return Optional.empty();
            return Optional.of(new IfElse(
                    usefulTokens.get(0).getLine(),
                    usefulTokens.get(0).getIndex(),
                    ifStatements,
                    elseStatements,
                    condition.get()
            ));
        } else {
            return Optional.empty();
        }
    }

    private Optional<Integer> indexOf(List<TokenType> tokenTypes, TokenType type) {
        return Optional.of(tokenTypes.indexOf(type)).filter(index -> index != -1);
    }

    private Optional<Integer> lastIndexOf(List<TokenType> tokenTypes, TokenType type) {
        return Optional.of(tokenTypes.lastIndexOf(type)).filter(index -> index != -1);
    }
}
