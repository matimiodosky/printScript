package com.austral.ingsis.impl;


import com.austral.ingsis.Lexer;
import com.austral.ingsis.Token;
import com.austral.ingsis.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.austral.ingsis.impl.Matcher.matchType;


public class LexerImpl implements Lexer {

    @Override
    public Stream<Token<?>> scan(Stream<Character> source) {

        List<Character> input = source.collect(Collectors.toList());
        List<Token<?>> outPut = new ArrayList<>();

        var i = 0;
        var acc = "";
        var line = 0;

        while (i < input.size()) {
            acc = acc.concat(input.get(i) + "");
            Optional<TokenType> tokenType = matchType(acc);
            if (tokenType.isPresent()) {
                outPut.add(
                        Token
                                .builder()
                                .line(line)
                                .type(tokenType.get())
                                .lexeme(acc)
                                .build()
                );
                acc = "";
            }
            i++;
        }
        return outPut.stream();
    }

}
