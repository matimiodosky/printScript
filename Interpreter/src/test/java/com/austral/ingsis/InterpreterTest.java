package com.austral.ingsis;

import com.austral.ingsis.impl.LexerImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InterpreterTest {

    @Test
    public void test001Print() {

        Lexer lexer = new LexerImpl();
        String code = """
                print("hola");
        """;


        Stream<Character> characterStream = code.chars().mapToObj(i -> (char) i);
        Stream<Token> tokenStream = lexer.scan(characterStream);

        Parser parser = new ParserImpl();
        Stream<Statement> statements = parser.parse(tokenStream);

        Interpreter interpreter = new InterpreterImpl();
        Stream<Character> out = interpreter.interpret(statements);

        String outString = out
                .map(Objects::toString)
                .collect(Collectors.joining());

        assertEquals("hola\n", outString);
    }

    @Test
    public void test002PrintDefinedVariable() {

        Lexer lexer = new LexerImpl();
        String code = """
                const a: string = "hola";
                print(a);
        """;


        Stream<Character> characterStream = code.chars().mapToObj(i -> (char) i);
        Stream<Token> tokenStream = lexer.scan(characterStream, Collections.singletonList(TokenType.CONST));

        Parser parser = new ParserImpl();
        Stream<Statement> statements = parser.parse(tokenStream);

        Interpreter interpreter = new InterpreterImpl();
        Stream<Character> out = interpreter.interpret(statements);

        String outString = out
                .map(Objects::toString)
                .collect(Collectors.joining());

        assertEquals("hola\n", outString);
    }

    @Test
    public void test003PrintRedefinedVariable() {

        Lexer lexer = new LexerImpl();
        String code = """
                let a: string = "hola";
                a = "chau";
                print(a);
        """;

        Stream<Character> characterStream = code.chars().mapToObj(i -> (char) i);
        Stream<Token> tokenStream = lexer.scan(characterStream);

        Parser parser = new ParserImpl();
        Stream<Statement> statements = parser.parse(tokenStream);

        Interpreter interpreter = new InterpreterImpl();
        Stream<Character> out = interpreter.interpret(statements);

        String outString = out
                .map(Objects::toString)
                .collect(Collectors.joining());

        assertEquals("chau\n", outString);
    }

    @Test(expected = RuntimeException.class)
    public void test004ReassignmentToConstVariable() {

        Lexer lexer = new LexerImpl();
        String code = """
                const a: string = "hola";
                a = "chau";
                print(a);
        """;

        Stream<Character> characterStream = code.chars().mapToObj(i -> (char) i);
        Stream<Token> tokenStream = lexer.scan(characterStream);

        Parser parser = new ParserImpl();
        Stream<Statement> statements = parser.parse(tokenStream);

        Interpreter interpreter = new InterpreterImpl();
        interpreter.interpret(statements);
    }




}
