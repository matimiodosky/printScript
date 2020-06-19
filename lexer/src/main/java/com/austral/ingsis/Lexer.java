package com.austral.ingsis;

import java.util.List;
import java.util.stream.Stream;

public interface Lexer {

    Stream<Token> scan(Stream<Character> source);


    Stream<Token> scan(Stream<Character> characterStream, List<TokenType> enabledOptionalFeatures);

}
