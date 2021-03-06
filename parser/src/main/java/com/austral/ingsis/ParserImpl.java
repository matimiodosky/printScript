package com.austral.ingsis;

import com.austral.ingsis.exception.ParsignError;
import com.austral.ingsis.matchers.ExpressionMatcher;
import com.austral.ingsis.matchers.expression.*;
import com.austral.ingsis.matchers.statement.*;
import com.austral.ingsis.matchers.StatementMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParserImpl implements Parser, StatementParser, ExpressionParser {

    private final List<StatementMatcher<? extends Statement>> statementMatchers = Arrays.asList(
            new ImportMatcher(this),
            new VariableDefinitionMatcher(this),
            new VariableAssignmentMatcher(this),
            new VariableExplicitDefinitionMatcher(this),
            new VariableExplicitDefinitionWithNoValueMatcher(this),
            new PrintMatcher(this),
            new IfElseMatcher(this, this),
            new IfMatcher(this, this)
            );

    private final List<ExpressionMatcher<? extends Expression>> expressionMatchers = Arrays.asList(
            new OperationMatcher(this),
            new GeometricOperationMatcher(this),
            new LiteralNumberMatcher(this),
            new LiteralStringMatcher(this),
            new LiteralBooleanMatcher(this),
            new IdentifierMatcher(this)
    );

    @Override
    public Stream<Statement> parse(Stream<Token> tokenStream) {
        List<Token> tokens = tokenStream
                .filter(this::usefulToken)
                .collect(Collectors.toList());
        List<Token> acc = new ArrayList<>();
        List<Statement> statements = new ArrayList<>();
        int index = 0;
        Optional<Statement> statement = Optional.empty();
        while (statement.isEmpty() && index < tokens.size()){
            acc.add(tokens.get(index));
            index = index + 1;
            statement = parseStatementToOptional(acc, index < tokens.size() ? tokens.get(index): null);
            statement.ifPresent(statements::add);
        }
        if (tokens.isEmpty()) return statements.stream();
        if (index == tokens.size() && statement.isEmpty()){
            throw new ParsignError(
                    "unsfinished statement",
                    tokens.get(tokens.size() - 1).getLine(),
                    tokens.get(tokens.size() - 1).getIndex()
                    );
        }
        return Stream.concat(statements.stream(), parse(tokens.subList(index, tokens.size()).stream()));
    }

    private boolean usefulToken(Token token) {
        return token.getType() != TokenType.WHITESPACE &&
                token.getType() != TokenType.NEWLINE;
    }

    private Optional<Statement> parseStatementToOptional(List<Token> token, Token peek) {
        try {
            return Optional.of(parseStatement(token, peek));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Statement parseStatement(List<Token> tokens, Token peek) {
        return statementMatchers
                .stream()
                .map(matcher -> {
                    return matcher.match(tokens.stream(), peek);
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseThrow(() -> this.statementError(tokens));
    }

    private ParsignError statementError(List<Token> tokens) {
        Token lastToken = tokens.get(tokens.size() - 1);
        return new ParsignError(String.format("Invalid statement at: [%d, %d]", lastToken.getLine(), lastToken.getIndex()), lastToken.getLine(), lastToken.getIndex());
    }

    @Override
    public Optional<? extends Expression> parseExpression(List<Token> tokens) {
        return expressionMatchers
                .stream()
                .map(matcher -> matcher.match(tokens.stream()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findAny();
    }

}
