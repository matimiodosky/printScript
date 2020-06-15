package com.austral.ingsis.matchers;

import com.austral.ingsis.*;
import com.austral.ingsis.Statement;

public abstract class StatementMatcher<T extends Statement> extends Matcher<T> {

    protected final ExpressionParser parser;

    public StatementMatcher(ExpressionParser parser) {
        this.parser = parser;
    }


}
