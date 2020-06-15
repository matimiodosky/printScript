package com.austral.ingsis;

import com.austral.ingsis.exception.SyntaxError;
import com.austral.ingsis.matchers.ExpressionMatcher;
import com.austral.ingsis.matchers.expression.LiteralNumberMatcher;
import com.austral.ingsis.matchers.expression.LiteralStringMatcher;
import com.austral.ingsis.matchers.statement.VariableAssignmentMatcher;
import com.austral.ingsis.matchers.statement.VariableDefinitionMatcher;
import com.austral.ingsis.matchers.StatementMatcher;

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
            new LiteralNumberMatcher(),
            new LiteralStringMatcher()
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
                .orElseThrow(() -> this.statementError(tokens));
    }

    private SyntaxError statementError(List<Token> tokens) {
        Token lastToken = tokens.get(tokens.size() - 1);
        return new SyntaxError(String.format("Invalid statement at: [%d, %d]", lastToken.getLine(), lastToken.getIndex()), lastToken.getLine(), lastToken.getIndex());
    }

    private SyntaxError expressionError(List<Token> tokens) {
        Token lastToken = tokens.get(tokens.size() - 1);
        return new SyntaxError(String.format("Invalid expression at: [%d, %d]", lastToken.getLine(), lastToken.getIndex()), lastToken.getLine(), lastToken.getIndex());
    }


    @Override
    public Expression parseExpression(List<Token> tokens) {
        return expressionParsers
                .stream()
                .map(matcher -> matcher.match(tokens.stream()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findAny()
                .orElseThrow(() -> this.expressionError(tokens));
    }


    private Stream<List<Token>> splitIntoStatements(Stream<Token> tokens) {
        final List<List<Token>> statements = new ArrayList<>();
        List<Token> currentStatement = new ArrayList<>();

        for (Token token : tokens.collect(Collectors.toList())) {
            currentStatement.add(token);
            if (token.getType() == TokenType.SEMICOLON) {
                statements.add(currentStatement);
                currentStatement = new ArrayList<>();
            }
        }

        if (!currentStatement
                .stream()
                .allMatch(x -> x.getType() == TokenType.WHITESPACE || x.getType() == TokenType.NEWLINE)
        ) {
            Token lastToken = currentStatement.get(currentStatement.size() - 1);
            throw new SyntaxError(String.format("Unfinished statement at: [%d, %d]", lastToken.getLine(), lastToken.getIndex()), lastToken.getLine(), lastToken.getIndex());
        }

        return statements.stream();
    }

}
