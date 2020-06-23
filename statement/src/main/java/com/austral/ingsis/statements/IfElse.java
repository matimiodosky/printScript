package com.austral.ingsis.statements;


import com.austral.ingsis.Expression;
import com.austral.ingsis.Statement;

import java.util.List;
import java.util.stream.Stream;

/**
 * (const | let) (identifier) (equals) (literal | number | expression) (semicolon)
 */
public class IfElse extends Statement {

    private final Stream<Statement> ifStatements;
    private final Stream<Statement> elseStatements;
    private final Expression condition;

    public IfElse(int line, int index, Stream<Statement> ifStatements, Stream<Statement> elseStatements, Expression condition) {
        super(line, index);
        this.ifStatements = ifStatements;
        this.elseStatements = elseStatements;
        this.condition = condition;
    }

    public Stream<Statement> getIfStatements() {
        return ifStatements;
    }

    public Stream<Statement> getElseStatements() {
        return elseStatements;
    }

    public Expression getCondition() {
        return condition;
    }
}
