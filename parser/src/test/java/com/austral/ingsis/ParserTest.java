package com.austral.ingsis;

import com.austral.ingsis.statements.Statement;
import org.junit.Test;

import java.util.stream.Stream;

public class ParserTest {


    @Test
    public void tes001AssignmentIsParsed() {

        Stream<Token> tokenStream = Stream.of(
                new Token(TokenType.LET, "let"),
                new Token(TokenType.WHITESPACE, " "),
                new Token(TokenType.IDENTIFIER, "a"),
                new Token(TokenType.ASSIGNATION, "="),
                new Token(TokenType.NUMBER, "0"),
                new Token(TokenType.SEMICOLON, ";")
        );

        Parser parser = new ParserImpl();
        Stream<Statement> parse = parser.parse(tokenStream);
        parse.forEach(System.out::println);


    }


}
