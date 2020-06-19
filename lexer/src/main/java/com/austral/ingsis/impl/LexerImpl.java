package com.austral.ingsis.impl;

import com.austral.ingsis.Lexer;
import com.austral.ingsis.Token;
import com.austral.ingsis.TokenType;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LexerImpl implements Lexer {

    private int currentLine = 0;
    private int currentIndex = 0;
    private final TokenPatternProvider tokenPatternProvider = new TokenPatternProvider();

    public Stream<Token> scan(Stream<Character> characterStream, List<TokenType> enabledOptionalFeatures) {
        ArrayList<Token> tokens = new ArrayList<>();
        Matcher matcher = getMatcher(characterStream);
        while (matcher.find()) {
            tokens.add(
                    tokenPatternProvider.getValues(enabledOptionalFeatures)
                            .filter(tokenType -> matcher.group(tokenType.name()) != null)
                            .findAny()
                            .map(tokenType -> new Token(tokenType, matcher.group(tokenType.name()), this.currentLine, this.currentIndex))
                            .map(this::advanceCurrentIndex)
                            .map(this::checkNewLine)
                            .flatMap(this::checkError)
                            .orElseThrow(() -> new SyntaxError(this.currentLine, this.currentIndex)));
        }
        return tokens.stream();
    }

    public Stream<Token> scan(Stream<Character> characterStream) {
        return this.scan(characterStream, Collections.emptyList());
    }

    private Token advanceCurrentIndex(Token token) {
        this.currentIndex += token.getData().length();
        return token;
    }

    private Optional<Token> checkError(Token token) {
        return token.getType() == TokenType.INVALIDTOKEN ? Optional.empty() : Optional.of(token);
    }

    private Token checkNewLine(Token token) {
        if (token.getType() == TokenType.NEWLINE) {
            this.currentLine++;
            this.currentIndex = 0;
        }
        return token;
    }

    private Matcher getMatcher(Stream<Character> input) {
        return Pattern.compile(
                Arrays.stream(TokenType.values())
                        .map(tokenType -> String.format("|(?<%s>%s)", tokenType.name(), tokenPatternProvider.get(tokenType)))
                        .collect(Collectors.joining())
                        .substring(1)
        ).matcher(input
                .map(Objects::toString)
                .collect(Collectors.joining())
        );
    }
}
