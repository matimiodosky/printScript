package com.austral.ingsis.impl;

import com.austral.ingsis.Token;
import com.austral.ingsis.TokenType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LexerImpl {

    private int currentLine = 0;

    private Token checkNewLine(Token token){
        if (token.type == TokenType.NEWLINE) this.currentLine++;
        return token;
    }

    public ArrayList<Token> lex(String input) {
        ArrayList<Token> tokens = new ArrayList<>();
        Matcher matcher = getMatcher(input);
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
        return tokens;
    }

    private Optional<Token> checkError(Token token) {
       return token.type == TokenType.INVALIDTOKEN ? Optional.empty() : Optional.of(token);
    }

    private Matcher getMatcher(String input) {
        return Pattern.compile(Arrays.stream(TokenType.values())
                .map(tokenType -> String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern))
                .collect(Collectors.joining())
                .substring(1)
        ).matcher(input);
    }
}
