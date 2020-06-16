package com.austral.ingsis.statements;


import com.austral.ingsis.Expression;
import com.austral.ingsis.Statement;

/**
 * (const | let) (identifier) (equals) (literal | number | expression) (semicolon)
 */
public class Print extends Statement {

    private final Expression<?> value;

    public Print( Expression<?> value, int line, int index) {

        super(line, index);
        this.value = value;
    }

    public Expression<?> getValue() {
        return value;
    }
}
