package com.austral.ingsis;

import java.util.stream.Stream;

public interface Lexer {

    Stream<Token<?>> scan(Stream<Character> source);

}
