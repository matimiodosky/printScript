package com.austral.ingsis.statements;


import com.austral.ingsis.Expression;
import com.austral.ingsis.Statement;

import java.util.List;

/**
 * (const | let) (identifier) (equals) (literal | number | expression) (semicolon)
 */
public class Import extends Statement {

    private final Expression path;


    public Import(int line, int index, Expression path) {
        super(line, index);
        this.path = path;
    }

    public Expression getPath() {
        return path;
    }
}
