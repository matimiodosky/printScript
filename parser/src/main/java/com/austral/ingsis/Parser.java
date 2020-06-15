package com.austral.ingsis;

import java.util.stream.Stream;

public interface Parser {

    Stream<Statement> parse(Stream<Token> tokens);
}
