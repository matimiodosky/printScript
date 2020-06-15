package com.austral.ingsis;

import java.util.stream.Stream;

public interface Interpreter {

    Stream<Character> interpret(Stream<Statement> statements);

}
