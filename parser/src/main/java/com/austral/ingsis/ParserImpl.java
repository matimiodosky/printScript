package com.austral.ingsis;

import com.austral.ingsis.parsers.VariableAssignmentMatcher;
import com.austral.ingsis.parsers.VariableDefinitionMatcher;
import com.austral.ingsis.parsers.StatementMatcher;
import com.austral.ingsis.statements.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParserImpl implements Parser {

    private final List<StatementMatcher<?>> parsers = Arrays.asList(
            new VariableDefinitionMatcher(),
            new VariableAssignmentMatcher()
    );

    @Override
    public Stream<Statement> parse(Stream<Token> tokens) {

        return splitIntoStatements(tokens)
                .map(this::parseStatement);

    }

    private Statement parseStatement(Statement statement) {
        return parsers
                .stream()
                .map(matcher -> matcher.match(statement.getTokens().stream()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findAny()
                .orElseThrow(SyntaxError::new);
    }

    private Stream<Statement> splitIntoStatements(Stream<Token> tokens) {
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

        return statements
                .stream()
                .map(Statement::new);
    }


}
