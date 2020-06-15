package com.austral.ingsis.impl;

import com.austral.ingsis.TokenType;

import java.util.EnumMap;

public class TokenPatternProvider {
    private static final EnumMap<TokenType, String> patterns = new EnumMap<>(TokenType.class);

    public String get(TokenType tokenType) {
        if (patterns.isEmpty()) fill();
        return patterns.get(tokenType);
    }

    private void fill() {
        patterns.put(TokenType.LET,"let");
        patterns.put(TokenType.CONST,"const");
        patterns.put(TokenType.COLON,":");
        patterns.put(TokenType.BOOLEANTYPE,"boolean");
        patterns.put(TokenType.NUMBERTYPE,"number");
        patterns.put(TokenType.STRINGTYPE,"string");
        patterns.put(TokenType.LITERAL,"\"([_a-zA-Z0-9])*\"");
        patterns.put(TokenType.SEMICOLON,";");
        patterns.put(TokenType.PLUS,"[+]");
        patterns.put(TokenType.MINUS,"[-]");
        patterns.put(TokenType.MULT,"[*]");
        patterns.put(TokenType.DIV,"[/]");
        patterns.put(TokenType.GRATEREQUAL,">=");
        patterns.put(TokenType.GRATER,"[>]");
        patterns.put(TokenType.LESSEQUAL,"<=");
        patterns.put(TokenType.LESS,"[<]");
        patterns.put(TokenType.ASSIGNATION,"=");
        patterns.put(TokenType.NUMBER,"-?[0-9]+");
        patterns.put(TokenType.IF,"if");
        patterns.put(TokenType.ELSE,"else");
        patterns.put(TokenType.OPENBRACE,"[{]");
        patterns.put(TokenType.CLOSEBRACE,"[}]");
        patterns.put(TokenType.IMPORT,"import");
        patterns.put(TokenType.PRINT,"print");
        patterns.put(TokenType.IDENTIFIER,"[_a-zA-Z][_a-zA-Z0-9]*");
        patterns.put(TokenType.WHITESPACE,"[ \t\f\r]+");
        patterns.put(TokenType.NEWLINE,"\n");
        patterns.put(TokenType.INVALIDTOKEN,".+");

    }
}
