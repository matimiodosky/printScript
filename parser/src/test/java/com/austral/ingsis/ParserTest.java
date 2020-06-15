package com.austral.ingsis;

import com.austral.ingsis.exception.SyntaxError;
import com.austral.ingsis.expression.LiteralString;
import com.austral.ingsis.impl.LexerImpl;
import com.austral.ingsis.statements.VariableAssignment;
import com.austral.ingsis.statements.VariableDefinition;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParserTest {


    @Test
    public void tbs001VariableDefiniteIsParsed() {

        Lexer lexer = new LexerImpl();
        String code = """
                let a = 0;
                """;
        Stream<Character> characterStream = code.chars().mapToObj(i -> (char) i);

        Stream<Token> tokenStream = lexer.scan(characterStream);

        Parser parser = new ParserImpl();
        List<Statement> parse = parser.parse(tokenStream).collect(Collectors.toList());
        parse.forEach(System.out::println);
        assertTrue(parse.get(0) instanceof VariableDefinition);

    }

    @Test
    public void tes002VariableAssignmentIsParsed() {

        Lexer lexer = new LexerImpl();
        String code = """
                a = 0;
                """;
        Stream<Character> characterStream = code.chars().mapToObj(i -> (char) i);

        Stream<Token> tokenStream = lexer.scan(characterStream);


        Parser parser = new ParserImpl();
        List<Statement> parse = parser.parse(tokenStream).collect(Collectors.toList());
        parse.forEach(System.out::println);
        assertTrue(parse.get(0) instanceof VariableAssignment);

    }

    @Test
    public void tes003VariableAssignmentWithLiteralValueIsParsed() {

        Lexer lexer = new LexerImpl();
        String code = """
                a = "a";
                """;

        Stream<Character> characterStream = code.chars().mapToObj(i -> (char) i);

        Stream<Token> tokenStream = lexer.scan(characterStream);

        Parser parser = new ParserImpl();
        List<Statement> statements = parser.parse(tokenStream).collect(Collectors.toList());
        assertTrue(statements.get(0) instanceof VariableAssignment);
        assertTrue(((VariableAssignment) statements.get(0)).getValue() instanceof LiteralString);
    }

    @Test
    public void test004InvalidStatementAtLine0() {
        Lexer lexer = new LexerImpl();
        String code = """
                a;
                """;
        Stream<Character> characterStream = code.chars().mapToObj(i -> (char) i);

        Stream<Token> tokenStream = lexer.scan(characterStream);


        Parser parser = new ParserImpl();
        try {
            parser.parse(tokenStream);
        } catch (SyntaxError e) {
            assertEquals(0, e.getLine());
        }

    }

    @Test
    public void test004InvalidStatementAtLine1() {
        Lexer lexer = new LexerImpl();
        String code = """
                let a = 0;
                a;
                """;
        Stream<Character> characterStream = code.chars().mapToObj(i -> (char) i);
        Stream<Token> tokenStream = lexer.scan(characterStream);

        Parser parser = new ParserImpl();
        try {
            parser.parse(tokenStream);
        } catch (SyntaxError e) {
            assertEquals(1, e.getLine());
        }

    }

    @Test
    public void test005Variables() {
        Lexer lexer = new LexerImpl();

        String code = """
                       let x: number = 5;
                       let y: string = "Something";
                       let z: string = 'AnotherThing';
                       let a: boolean = true;   
                       
                       print(x);
                       print(y);
                       print(z);
                       print(a);
                                             
                """;

        Stream<Character> characterStream = code.chars().mapToObj(i -> (char) i);
        Stream<Token> tokenStream = lexer.scan(characterStream);

        Parser parser = new ParserImpl();
        List<Statement> parse = parser.parse(tokenStream).collect(Collectors.toList());
        System.out.println();

    }


}
