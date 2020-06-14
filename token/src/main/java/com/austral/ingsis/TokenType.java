package com.austral.ingsis;

public enum TokenType {
    // Token types cannot have underscores
    LET("let"),
    CONST("const"),
    COLON(":"),
    BOOLEANTYPE("boolean"),
    NUMBERTYPE("number"),
    STRINGTYPE("string"),
    LITERAL("\"([_a-zA-Z][_a-zA-Z0-9])*\""),
    SEMICOLON(";"),
    PLUS("[+]"),
    MINUS("[-]"),
    MULT("[*]"),
    DIV("[/]"),
    GRATEREQUAL(">="),
    GRATER("[>]"),
    LESSEQUAL("<="),
    LESS("[<]"),
    ASSIGNATION("="),
    NUMBER("-?[0-9]+"),
    IF("if"),
    ELSE("else"),
    OPENBRACE("[{]"),
    CLOSEBRACE("[}]"),
    IMPORT("import"),
    PRINT("print"),
    IDENTIFIER("[_a-zA-Z][_a-zA-Z0-9]{0,30}"),
    WHITESPACE("[ \t\f\r\n]+"),
    INVALIDTOKEN(".+" );

    public final String pattern;

    TokenType(String pattern) {
        this.pattern = pattern;
    }

}
