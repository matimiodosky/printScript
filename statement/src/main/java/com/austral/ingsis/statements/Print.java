package com.austral.ingsis.statements;


import com.austral.ingsis.Expression;
import com.austral.ingsis.Statement;

/**
 * (const | let) (identifier) (equals) (literal | number | expression) (semicolon)
 */
public class Print extends Statement {

    Expression value;

    public Print( Expression value) {

        this.value = value;
    }

    public Expression getValue() {
        return value;
    }
}
