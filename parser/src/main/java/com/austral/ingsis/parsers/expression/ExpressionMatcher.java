package com.austral.ingsis.parsers.expression;

import com.austral.ingsis.Token;
import com.austral.ingsis.expression.Expression;
import com.austral.ingsis.parsers.Matcher;

import java.util.Optional;
import java.util.stream.Stream;

public abstract class ExpressionMatcher<T extends Expression> extends Matcher {

    public abstract Optional<Expression> match(Stream<Token> tokenStream);


}
