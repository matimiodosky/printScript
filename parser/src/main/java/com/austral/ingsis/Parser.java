package com.austral.ingsis;

import com.austral.ingsis.statements.Statement;

import java.util.stream.Stream;

public interface Parser {

    Stream<Statement> parse(Stream<Token> tokens);
}
