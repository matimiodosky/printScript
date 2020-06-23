package com.austral.ingsis;

import com.austral.ingsis.exception.ParsignError;
import com.austral.ingsis.expression.LiteralString;
import com.austral.ingsis.impl.LexerImpl;
import com.austral.ingsis.statements.*;
import com.sun.jdi.BooleanType;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
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
        assertTrue(((VariableAssignment) statements.get(0)).getExpression() instanceof LiteralString);
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
        } catch (ParsignError e) {
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
        } catch (ParsignError e) {
            assertEquals(1, e.getLine());
        }

    }

    @Test
    public void test005VariablesExample() {
        Lexer lexer = new LexerImpl();

        String code = """
                       let x: number = a;
                       let y: string = "Something";
                       let z: string = 'AnotherThing';
                       let a: boolean = true;   
                       
                       print(x);
                       print(y);
                       print(z);
                       print(a);
                                             
                """;

        Stream<Character> characterStream = code.chars().mapToObj(i -> (char) i);
        Stream<Token> tokenStream = lexer.scan(characterStream, Arrays.asList(TokenType.BOOLEANTYPE, TokenType.TRUELITERAL));

        Parser parser = new ParserImpl();
        List<Statement> parse = parser.parse(tokenStream).collect(Collectors.toList());

        assertTrue(parse.get(0) instanceof VariableExplicitDefinition);
        assertTrue(parse.get(1) instanceof VariableExplicitDefinition);
        assertTrue(parse.get(2) instanceof VariableExplicitDefinition);
        assertTrue(parse.get(3) instanceof VariableExplicitDefinition);

        assertTrue(parse.get(4) instanceof Print);
        assertTrue(parse.get(5) instanceof Print);
        assertTrue(parse.get(6) instanceof Print);

        System.out.println();

    }

    @Test
    public void test0006DeclareVaibalesWithSpacesExample(){
        Lexer lexer = new LexerImpl();

        // semi colons at the end of each statement is requerid for this istance of PrintScript
        // String is not a type. string is.

        String code = """
                       let         a   :                          boolean         =          5 ;
                       let    b
                              :
                                  string
                          =      "adasdasdasdasdasdasd";
                       
                                             
                """;

        Stream<Character> characterStream = code.chars().mapToObj(i -> (char) i);
        Stream<Token> tokenStream = lexer.scan(characterStream, Arrays.asList(TokenType.BOOLEANTYPE));

        Parser parser = new ParserImpl();
        List<Statement> parse = parser.parse(tokenStream).collect(Collectors.toList());

        assertTrue(parse.get(0) instanceof VariableExplicitDefinition);
        assertTrue(parse.get(1) instanceof VariableExplicitDefinition);
    }

    @Test
    public void test007VariableReassignExample(){
        Lexer lexer = new LexerImpl();

        // semi colons at the end of each statement is requerid for this istance of PrintScript
        // String is not a type. string is.

        String code = """
                       let x: number = 5;
                       let y: string = "AnotherThing";
                       
                       print(x);
                       print(y);
                       
                       y = "SomethingElse";
                       x = 18;
                       print(x);
                       print(y);
                       
                """;

        Stream<Character> characterStream = code.chars().mapToObj(i -> (char) i);
        Stream<Token> tokenStream = lexer.scan(characterStream);

        Parser parser = new ParserImpl();
        List<Statement> parse = parser.parse(tokenStream).collect(Collectors.toList());

        assertTrue(parse.get(0) instanceof VariableExplicitDefinition);
        assertTrue(parse.get(1) instanceof VariableExplicitDefinition);
        assertTrue(parse.get(2) instanceof Print);
        assertTrue(parse.get(3) instanceof Print);
        assertTrue(parse.get(4) instanceof VariableAssignment);
        assertTrue(parse.get(5) instanceof VariableAssignment);
        assertTrue(parse.get(6) instanceof Print);
        assertTrue(parse.get(7) instanceof Print);
    }

    @Test
    public void test0008VariableDeclarationWithNoValue(){
        Lexer lexer = new LexerImpl();

        // semi colons at the end of each statement is requerid for this istance of PrintScript
        // String is not a type. string is.

        String code = """
                       let y: string;
                       const x: number;
                       let z: boolean;
                       
                       print(x);
                       print(y);
                       print(z);
                """;

        Stream<Character> characterStream = code.chars().mapToObj(i -> (char) i);
        Stream<Token> tokenStream = lexer.scan(characterStream, Collections.singletonList(TokenType.BOOLEANTYPE));

        Parser parser = new ParserImpl();
        List<Statement> parse = parser.parse(tokenStream).collect(Collectors.toList());

        assertTrue(parse.get(0) instanceof VariableExplicitDefinitionWithNoValue);
        assertTrue(parse.get(1) instanceof VariableExplicitDefinitionWithNoValue);
        assertTrue(parse.get(2) instanceof VariableExplicitDefinitionWithNoValue);

        assertTrue(parse.get(3) instanceof Print);
        assertTrue(parse.get(4) instanceof Print);
        assertTrue(parse.get(5) instanceof Print);
    }

    @Test(expected = com.austral.ingsis.impl.SyntaxError.class)
    public void test0010SyntaxError(){
        Lexer lexer = new LexerImpl();

        // semi colons at the end of each statement is requerid for this istance of PrintScript
        // String is not a type. string is.

        String code = """
                       let a: bigint = 1209381029381029381023;
                       let b: Person = "new Person()"
                       
                """;

        Stream<Character> characterStream = code.chars().mapToObj(i -> (char) i);
        Stream<Token> tokenStream = lexer.scan(characterStream);

        Parser parser = new ParserImpl();
    }

}
