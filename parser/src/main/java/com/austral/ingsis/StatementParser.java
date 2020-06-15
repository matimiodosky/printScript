package com.austral.ingsis;

import com.austral.ingsis.statements.Statement;

import java.util.List;

public interface StatementParser {

    Statement parseStatement(List<Token> token);

}
