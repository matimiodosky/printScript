package com.austral.ingsis.matchers.statement;

import com.austral.ingsis.*;
import com.austral.ingsis.matchers.StatementMatcher;
import com.austral.ingsis.statements.If;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * (const | let) (identifier) (equals) (literal | number | expression) (semicolon)
 */
public class ElseMatcher extends StatementMatcher<If> {


    private final Parser statementParser;

    public ElseMatcher(ExpressionParser expressionParser, Parser statementParser) {
        super(expressionParser);
        this.statementParser = statementParser;
    }

    @Override
    public Optional<If> match(Stream<Token> tokens) {
        List<Token> usefulTokens = tokens
                .filter(super::usefulToken)
                .collect(Collectors.toList());

        if (usefulTokens.size() < 11) return Optional.empty();

        Optional<Token> keyWord = Optional.of(usefulTokens.get(0)).filter(token -> token.getType().equals(TokenType.IF));
        Optional<Token> openPar = Optional.of(usefulTokens.get(1)).filter(token -> token.getType().equals(TokenType.OPENPAR));

        int openBraceIndex = usefulTokens.stream().map(Token::getType).collect(Collectors.toList()).indexOf(TokenType.OPENBRACE);
        if (openBraceIndex == -1)return Optional.empty();

        int closingBraceIndex = usefulTokens.stream().map(Token::getType).collect(Collectors.toList()).lastIndexOf(TokenType.CLOSEBRACE);
        if (closingBraceIndex == -1)return Optional.empty();

        Optional<? extends Expression> condition = parser.parseExpression(usefulTokens.subList(2, openBraceIndex - 1));
        Stream<Statement> statementStream = statementParser.parse(usefulTokens.subList(openBraceIndex + 1, closingBraceIndex).stream());
        if (keyWord.isPresent() && openPar.isPresent() && condition.isPresent()){
            return Optional.of(new If(
                    usefulTokens.get(0).getLine(),
                    usefulTokens.get(0).getIndex(),
                    condition.get(),
                    statementStream.collect(Collectors.toList())
            ));
        }
        return Optional.empty();
    }
}
