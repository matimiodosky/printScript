package com.austral.ingsis.matchers;

import com.austral.ingsis.Expression;
import com.austral.ingsis.ExpressionParser;

public abstract class ExpressionMatcher<T extends Expression> extends Matcher<T> {

    protected final ExpressionParser expressionParser;

    protected ExpressionMatcher(ExpressionParser expressionMatcher) {
        this.expressionParser = expressionMatcher;
    }
}
