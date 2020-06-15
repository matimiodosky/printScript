package com.austral.ingsis;

import com.austral.ingsis.statements.VariableAssignment;
import com.austral.ingsis.statements.VariableDefinition;
import com.austral.ingsis.statements.Statement;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class ParserTest {


    @Test
    public void tes001VariableDefinitonIsParsed() {

        Stream<Token> tokenStream = Stream.of(
                new Token(TokenType.LET, "let"),
                new Token(TokenType.WHITESPACE, " "),
                new Token(TokenType.IDENTIFIER, "a"),
                new Token(TokenType.ASSIGNATION, "="),
                new Token(TokenType.NUMBER, "0"),
                new Token(TokenType.SEMICOLON, ";")
        );

        Parser parser = new ParserImpl();
        List<Statement> parse = parser.parse(tokenStream).collect(Collectors.toList());
        parse.forEach(System.out::println);
        assertTrue(parse.get(0) instanceof VariableDefinition);

    }

//    @Test
//    public void tes001VariableAssignmentIsParsed() {
//
//        Stream<Token> tokenStream = Stream.of(
//                new Token(TokenType.WHITESPACE, " "),
//                new Token(TokenType.IDENTIFIER, "a"),
//                new Token(TokenType.ASSIGNATION, "="),
//                new Token(TokenType.NUMBER, "0"),
//                new Token(TokenType.SEMICOLON, ";")
//        );
//
//        Parser parser = new ParserImpl();
//        List<Statement> parse = parser.parse(tokenStream).collect(Collectors.toList());
//        parse.forEach(System.out::println);
//        assertTrue(parse.get(0) instanceof VariableAssignment);
//
//    }
//
//    @Test
//    public void tes001VariableAssignmentWithLiteralValueIsParsed() {
//
//        Stream<Token> tokenStream = Stream.of(
//                new Token(TokenType.WHITESPACE, " "),
//                new Token(TokenType.IDENTIFIER, "a"),
//                new Token(TokenType.ASSIGNATION, "="),
//                new Token(TokenType.LITERAL, "\"a\""),
//                new Token(TokenType.SEMICOLON, ";")
//        );
//
//        Parser parser = new ParserImpl();
//        List<Statement> parse = parser.parse(tokenStream).collect(Collectors.toList());
//        parse.forEach(System.out::println);
//        assertTrue(parse.get(0) instanceof VariableAssignment);
//        assertTrue(((VariableAssignment<?>) parse.get(0)).getValue() instanceof String);
//    }


}
