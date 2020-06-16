package com.austral.ingsis;

import java.util.stream.Stream;

@FunctionalInterface
public interface ProgramParser {

    Stream<Statement> parse(Stream<Character> in);
}
