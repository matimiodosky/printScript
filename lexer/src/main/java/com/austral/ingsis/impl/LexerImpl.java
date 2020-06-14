package com.austral.ingsis.impl;

import com.austral.ingsis.Token;
import com.austral.ingsis.TokenType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class LexerImpl {

    public ArrayList<Token> lex(String input) {
        // The tokens to return
        ArrayList<Token> tokens = new ArrayList<>();

        Matcher matcher = getMatcher(input);

        while (matcher.find()) {
            tokens.add(Arrays.stream(TokenType
                    .values())
                    .filter(tokenType -> matcher.group(tokenType.name()) != null)
                    .findAny()
                    .filter(tokenType -> tokenType != TokenType.INVALIDTOKEN)
                    .map(tokenType -> new Token(tokenType, matcher.group(tokenType.name())))
                    .orElseThrow(SyntaxError::new));
        }
        return tokens;
    }

    private Matcher getMatcher(String input) {
        return Pattern.compile(Arrays.stream(TokenType.values())
                .map(tokenType -> String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern))
                .collect(Collectors.joining())
                .substring(1)
        ).matcher(input);
    }
}
