package com.austral.ingsis.matchers;

import com.austral.ingsis.*;
import com.austral.ingsis.matchers.Matcher;
import com.austral.ingsis.statements.Statement;

import java.util.Optional;
import java.util.stream.Stream;

public abstract class StatementMatcher<T extends Statement> extends Matcher<T> {

    protected ExpressionParser parser;

    public StatementMatcher(ExpressionParser parser) {
        this.parser = parser;
    }


}
