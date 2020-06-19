package com.austral.ingsis.impl;

import com.austral.ingsis.TokenType;

import java.util.*;
import java.util.stream.Stream;

public class TokenPatternProvider {
    private  final EnumMap<TokenType, String> patterns = new EnumMap<>(TokenType.class);

    private  final List<TokenType> disabledOptionalFeatures = Arrays.asList(
            TokenType.BOOLEANTYPE,
            TokenType.GRATER,
            TokenType.GRATEREQUAL,
            TokenType.LESS,
            TokenType.LESSEQUAL,
            TokenType.TRUELITERAL,
            TokenType.FALSELITERAL
    );

    public TokenPatternProvider() {
        fill();
    }

    public String get(TokenType tokenType) {
        return patterns.get(tokenType);
    }

    private void fill() {
        patterns.put(TokenType.LET, "let");
        patterns.put(TokenType.CONST, "const");
        patterns.put(TokenType.TRUELITERAL, "true");
        patterns.put(TokenType.FALSELITERAL, "false");
        patterns.put(TokenType.BOOLEANTYPE, "boolean");
        patterns.put(TokenType.NUMBERTYPE, "number");
        patterns.put(TokenType.STRINGTYPE, "string");
        patterns.put(TokenType.IF, "if");
        patterns.put(TokenType.ELSE, "else");
        patterns.put(TokenType.IMPORT, "import");
        patterns.put(TokenType.PRINT, "print");
        patterns.put(TokenType.COLON, ":");
        patterns.put(TokenType.LITERAL, "\"([_a-zA-Z0-9 !\\/.])*\"|'([_a-zA-Z0-9 !\\/.])*'");
        patterns.put(TokenType.SEMICOLON, ";");
        patterns.put(TokenType.PLUS, "[+]");
        patterns.put(TokenType.MINUS, "[-]");
        patterns.put(TokenType.MULT, "[*]");
        patterns.put(TokenType.DIV, "[/]");
        patterns.put(TokenType.GRATEREQUAL, ">=");
        patterns.put(TokenType.GRATER, "[>]");
        patterns.put(TokenType.LESSEQUAL, "<=");
        patterns.put(TokenType.LESS, "[<]");
        patterns.put(TokenType.ASSIGNATION, "=");
        patterns.put(TokenType.NUMBER, "-?[0-9.]+");
        patterns.put(TokenType.OPENBRACE, "[{]");
        patterns.put(TokenType.OPENPAR, "[(]");
        patterns.put(TokenType.CLOSEPAR, "[)]");
        patterns.put(TokenType.CLOSEBRACE, "[}]");
        patterns.put(TokenType.IDENTIFIER, "[_a-zA-Z][_a-zA-Z0-9]*");
        patterns.put(TokenType.WHITESPACE, "[ \t\f\r]+");
        patterns.put(TokenType.NEWLINE, "\n");
        patterns.put(TokenType.INVALIDTOKEN, ".+");
    }

    public Stream<TokenType> getValues(List<TokenType> enabledOptionalFeatures) {
        return patterns
                .keySet()
                .stream()
                .filter(type -> !disabledOptionalFeatures.contains(type) || enabledOptionalFeatures.contains(type));

    }
}
