package com.austral.ingsis.impl;

import com.austral.ingsis.Lexer;
import com.austral.ingsis.Token;
import com.austral.ingsis.TokenType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LexerImpl implements Lexer {

    private int currentLine = 0;

    private Token checkNewLine(Token token) {
        if (token.type == TokenType.NEWLINE) this.currentLine++;
        return token;
    }

    public Stream<Token> scan(Stream<Character> characterStream) {
        ArrayList<Token> tokens = new ArrayList<>();
        Matcher matcher = getMatcher(characterStream);
        while (matcher.find()) {
            tokens.add(
                    Arrays.stream(TokenType.values())
                            .filter(tokenType -> matcher.group(tokenType.name()) != null)
                            .findAny()
                            .map(tokenType -> new Token(tokenType, matcher.group(tokenType.name())))
                            .map(this::checkNewLine)
                            .flatMap(this::checkError)
                            .orElseThrow(() -> new SyntaxError(this.currentLine)));
        }
        return tokens.stream();
    }

    private Optional<Token> checkError(Token token) {
        return token.type == TokenType.INVALIDTOKEN ? Optional.empty() : Optional.of(token);
    }

    private Matcher getMatcher(Stream<Character> input) {
        return Pattern.compile(Arrays.stream(TokenType.values())
                .map(tokenType -> String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern))
                .collect(Collectors.joining())
                .substring(1)
        ).matcher(input
                .map(Objects::toString)
                .collect(Collectors.joining())
        );
    }
}
