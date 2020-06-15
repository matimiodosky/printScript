package com.austral.ingsis;


import com.austral.ingsis.impl.LexerImpl;
import com.austral.ingsis.impl.SyntaxError;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LexerTest {


    @Test
    public void test001AllTokensAreIdentified() {
        for (TokenType tokenType : TokenType.values()) {
            Stream<Character> input = "true false let const : hola boolean number string = \"hola\" ; + - * / > >= < <= 123 if else { } import print \n"
                    .chars()
                    .mapToObj(i -> (char) i);

            Lexer lexer = new LexerImpl();
            Stream<Token> tokens = lexer.scan(input);
            if (tokenType != TokenType.WHITESPACE && tokenType != TokenType.INVALIDTOKEN)
                assertTrue(
                        String.format("Token not found: %s", tokenType),
                        tokens.anyMatch(token -> token.getType().equals(tokenType))
                );
        }

    }

    @Test
    public void test002AllTokensAreIdentifiedOnce() {
        for (TokenType tokenType : TokenType.values()) {
            Stream<Character> input = "true false let const : hola boolean number string = \"hola\" ; + - * / > >= < <= 123 if else { } import print \n"
                    .chars()
                    .mapToObj(i -> (char) i);
            Lexer lexer = new LexerImpl();
            var tokens = lexer.scan(input);
            if (tokenType != TokenType.WHITESPACE && tokenType != TokenType.INVALIDTOKEN)
                assertEquals(
                        String.format("Token found more than once: %s", tokenType),
                        1,
                        tokens
                                .filter(token -> token.getType().equals(tokenType))
                                .peek(System.out::println)
                                .count()
                );
        }
    }

    @Test(expected = SyntaxError.class)
    public void test003SyntaxError() {

        Stream<Character> input = "\"hola"
                .chars()
                .mapToObj(i -> (char) i);
        Lexer lexer = new LexerImpl();
        lexer.scan(input);

    }

    @Test
    public void test004SyntaxErrorAtSecondLine() {
        int line = -1;
        Stream<Character> input = "\n\"hola"
                .chars()
                .mapToObj(i -> (char) i);
        Lexer lexer = new LexerImpl();
        try {
            lexer.scan(input);
        } catch (SyntaxError error) {
            line = error.getLine();
        }
        assertEquals(1, line);
    }

    @Test
    public void test005SyntaxErrorAtThirdLine() {
        int line = -1;
        Stream<Character> input = "\n\n\"hola"
                .chars()
                .mapToObj(i -> (char) i);
        Lexer lexer = new LexerImpl();
        try {
            lexer.scan(input);
        } catch (SyntaxError error) {
            line = error.getLine();
        }
        assertEquals(2, line);
    }

    @Test
    public void test006SyntaxErrorAtIndex() {
        int index = -1;
        Stream<Character> input = "let let let \"hola"
                .chars()
                .mapToObj(i -> (char) i);
        Lexer lexer = new LexerImpl();
        try {
            lexer.scan(input);
        } catch (SyntaxError error) {
            index = error.getIndex();
        }
        assertEquals(17, index);
    }

    @Test
    public void test00tTrueScanned() {
        Stream<Character> input = "true"
                .chars()
                .mapToObj(i -> (char) i);

        Lexer lexer = new LexerImpl();

        Stream<Token> scan = lexer.scan(input);
        assertEquals(TokenType.TRUELITERAL, scan.collect(Collectors.toList()).get(0).getType());
    }


}
