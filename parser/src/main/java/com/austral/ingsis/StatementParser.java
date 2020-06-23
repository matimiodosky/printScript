package com.austral.ingsis;

import java.util.List;

public interface StatementParser {

    Statement parseStatement(List<Token> token, Token peek);

}
