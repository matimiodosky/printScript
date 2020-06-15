package com.austral.ingsis;

import com.austral.ingsis.exception.SyntaxError;
import com.austral.ingsis.expression.LiteralString;
import com.austral.ingsis.statements.VariableAssignment;
import com.austral.ingsis.statements.VariableDefinition;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParserTest {


    @Test
    public void tbs001VariableDefiniteIsParsed() {

        Stream<Token> tokenStream = Stream.of(
                new Token(TokenType.LET, "let", 0, 0),
                new Token(TokenType.WHITESPACE, " ", 0, 3),
                new Token(TokenType.IDENTIFIER, "a", 0, 4),
                new Token(TokenType.ASSIGNATION, "=", 0, 5),
                new Token(TokenType.NUMBER, "0", 0, 6),
                new Token(TokenType.SEMICOLON, ";", 0, 7)
        );

        Parser parser = new ParserImpl();
        List<Statement> parse = parser.parse(tokenStream).collect(Collectors.toList());
        parse.forEach(System.out::println);
        assertTrue(parse.get(0) instanceof VariableDefinition);

    }

    @Test
    public void tes002VariableAssignmentIsParsed() {

        Stream<Token> tokenStream = Stream.of(
                new Token(TokenType.IDENTIFIER, "a", 0, 0),
                new Token(TokenType.ASSIGNATION, "=", 0, 1),
                new Token(TokenType.NUMBER, "0", 0, 2),
                new Token(TokenType.SEMICOLON, ";", 0, 3)
        );

        Parser parser = new ParserImpl();
        List<Statement> parse = parser.parse(tokenStream).collect(Collectors.toList());
        parse.forEach(System.out::println);
        assertTrue(parse.get(0) instanceof VariableAssignment);

    }

    @Test
    public void tes003VariableAssignmentWithLiteralValueIsParsed() {

        Stream<Token> tokenStream = Stream.of(
                new Token(TokenType.IDENTIFIER, "a", 0, 0),
                new Token(TokenType.ASSIGNATION, "=", 0, 1),
                new Token(TokenType.LITERAL, "\"a\"", 0, 2),
                new Token(TokenType.SEMICOLON, ";", 0, 5)
        );

        Parser parser = new ParserImpl();
        List<Statement> statements = parser.parse(tokenStream).collect(Collectors.toList());
        assertTrue(statements.get(0) instanceof VariableAssignment);
        assertTrue(((VariableAssignment) statements.get(0)).getValue() instanceof LiteralString);
    }

    @Test
    public void test004InvalidStatementAtLine0(){
        Stream<Token> tokenStream = Stream.of(
                new Token(TokenType.IDENTIFIER, "a", 0, 0)
        );

        Parser parser = new ParserImpl();
        try {
            parser.parse(tokenStream);
        }catch (SyntaxError e){
            assertEquals(0, e.getLine());
        }

    }

    @Test
    public void test004InvalidStatementAtLine1(){
        Stream<Token> tokenStream = Stream.of(
                new Token(TokenType.IDENTIFIER, "a", 1, 0)
        );

        Parser parser = new ParserImpl();
        try {
            parser.parse(tokenStream);
        }catch (SyntaxError e){
            assertEquals(1, e.getLine());
        }

    }



}
