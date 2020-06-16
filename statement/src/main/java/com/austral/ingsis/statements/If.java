package com.austral.ingsis.statements;


import com.austral.ingsis.Expression;
import com.austral.ingsis.Statement;

import java.util.List;

/**
 * (const | let) (identifier) (equals) (literal | number | expression) (semicolon)
 */
public class If extends Statement {

    private final Expression condition;

    private final List<Statement> innerStatements;

    public If(int line, int index, Expression condition, List<Statement> innerStatements) {
        super(line, index);
        this.condition = condition;
        this.innerStatements = innerStatements;
    }

    public Expression getCondition() {
        return condition;
    }

    public List<Statement> getInnerStatements() {
        return innerStatements;
    }
}
