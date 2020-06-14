package com.austral.ingsis;


import com.austral.ingsis.impl.LexerImpl;
import com.austral.ingsis.impl.SyntaxError;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LexerTest {


    @Test
    public void test001() {
        assertTrue(true);
    }

    @Test
    public void test002AllTokensAreIdentified() {
        String input = "let const : hola boolean number string = \"hola\" ; + - * / > >= < <= 123 if else { } import print";

        // Create tokens and print them
        var tokens = new LexerImpl().lex(input);

        for (TokenType tokenType : TokenType.values()) {
            if(tokenType != TokenType.WHITESPACE && tokenType != TokenType.INVALIDTOKEN)
                assertTrue(
                    String.format("Token not found: %s", tokenType),
                    tokens
                            .stream()
                            .anyMatch(token -> token.type.equals(tokenType))
            );

            if(tokenType != TokenType.WHITESPACE && tokenType != TokenType.INVALIDTOKEN)
            assertEquals(
                    String.format("Token found more than once: %s", tokenType),
                    1,
                    tokens
                            .stream()
                            .filter(token -> token.type.equals(tokenType))
                            .count()
            );
        }

    }

    @Test(expected = SyntaxError.class)
    public void test003SyntaxError(){
        String input = "\"hola";
        new LexerImpl().lex(input);
    }


}
