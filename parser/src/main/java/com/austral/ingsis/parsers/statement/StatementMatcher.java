package com.austral.ingsis.parsers.statement;

import com.austral.ingsis.*;
import com.austral.ingsis.parsers.Matcher;
import com.austral.ingsis.statements.Statement;

import java.util.Optional;
import java.util.stream.Stream;

public abstract class StatementMatcher<T extends Statement> extends Matcher {

    protected ExpressionParser parser;

    public StatementMatcher(ExpressionParser parser) {
        this.parser = parser;
    }

    public abstract Optional<T> match(Stream<Token> tokens);


}
