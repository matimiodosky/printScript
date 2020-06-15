package com.austral.ingsis.parsers;

import com.austral.ingsis.statements.Statement;

import java.util.Optional;

public abstract class StatementMatcher<T extends Statement> {

    public abstract Optional<T> match(Statement statement);
}
