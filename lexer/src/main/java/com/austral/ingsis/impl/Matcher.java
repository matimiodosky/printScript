package com.austral.ingsis.impl;

import com.austral.ingsis.TokenType;

import java.util.Optional;

import static com.austral.ingsis.TokenType.*;

public class Matcher {

    public static Optional<TokenType> matchType(String possibleToken) {

        return Optional.ofNullable( switch (possibleToken) {
            case "(" -> LEFT_PAREN;
            case ")" -> RIGHT_PAREN;
            case "{" -> LEFT_BRACE;
            case "}" -> RIGHT_BRACE;
            case "," -> COMMA;
            case "." -> DOT;
            case "-" -> MINUS;
            case "+" -> PLUS;
            case ";" -> SEMICOLON;
            case "*" -> STAR;
            default -> null;
        });

    }
}
