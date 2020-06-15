package com.austral.ingsis;

import com.austral.ingsis.expression.Expression;
import com.austral.ingsis.expression.LiteralNumber;
import com.austral.ingsis.parsers.expression.ExpressionMatcher;
import com.austral.ingsis.parsers.expression.LiteralNumberMatcher;
import com.austral.ingsis.parsers.statement.VariableAssignmentMatcher;
import com.austral.ingsis.parsers.statement.VariableDefinitionMatcher;
import com.austral.ingsis.parsers.statement.StatementMatcher;
import com.austral.ingsis.statements.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParserImpl implements Parser, StatementParser, ExpressionParser {

    private final List<StatementMatcher<?>> statementMatchers = Arrays.asList(
            new VariableDefinitionMatcher(this),
            new VariableAssignmentMatcher(this)
    );

    private final List<ExpressionMatcher<?>> expressionParsers = Arrays.asList(
            new LiteralNumberMatcher()
    );

    @Override
    public Stream<Statement> parse(Stream<Token> tokens) {

        return splitIntoStatements(tokens).map(this::parseStatement);

    }

    @Override
    public Statement parseStatement(List<Token> tokens) {
        return statementMatchers
                .stream()
                .map(matcher -> matcher.match(tokens.stream()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findAny()
                .orElseThrow(SyntaxError::new);
    }

    @Override
    public Optional<Expression> parseExpression(List<Token> tokens) {
        return expressionParsers
                .stream()
                .map(matcher -> matcher.match(tokens.stream()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findAny();
    }

    private Stream<List<Token>> splitIntoStatements(Stream<Token> tokens) {
        final List<List<Token>> statements = new ArrayList<>();
        List<Token> currentStatement = new ArrayList<>();

        for (Token token : tokens.collect(Collectors.toList())) {
            currentStatement.add(token);
            if (token.type == TokenType.SEMICOLON) {
                statements.add(currentStatement);
                currentStatement = new ArrayList<>();
            }
        }

        if (!currentStatement.isEmpty()) throw new SyntaxError();

        return statements.stream();
    }


}
