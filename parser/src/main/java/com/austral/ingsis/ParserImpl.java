package com.austral.ingsis;

import com.austral.ingsis.exception.SyntaxError;
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
            new IfMatcher(this, this),
            new ImportMatcher(this),
            new VariableDefinitionMatcher(this),
            new VariableAssignmentMatcher(this),
            new VariableExplicitDefinitionMatcher(this),
            new VariableExplicitDefinitionWithNoValueMatcher(this),
            new PrintMatcher(this)
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
        List<Token> tokens = tokenStream.collect(Collectors.toList());
        List<Token> acc = new ArrayList<>();
        List<Statement> statements = new ArrayList<>();
        int index = 0;
        Optional<Statement> statement = Optional.empty();
        while (statement.isEmpty() && index < tokens.size()){
            acc.add(tokens.get(index));
            index = index + 1;
            statement = parseStatementToOptional(acc);
            statement.ifPresent(statements::add);
        }
        if (tokens.isEmpty()) return statements.stream();
        return Stream.concat(statements.stream(), parse(tokens.subList(index, tokens.size()).stream()));
    }

    private Optional<Statement> parseStatementToOptional(List<Token> token){
        try {
            return Optional.of(parseStatement(token));
        }catch (Exception e){
            return Optional.empty();
        }
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


    @Override
    public Optional<? extends Expression> parseExpression(List<Token> tokens) {
         return expressionMatchers
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
