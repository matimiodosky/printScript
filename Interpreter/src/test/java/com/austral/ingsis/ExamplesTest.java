package com.austral.ingsis;


import com.austral.ingsis.impl.LexerImpl;
import org.junit.Test;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class ExamplesTest {

    private void test(String code, String expectedOut) {
        Lexer lexer = new LexerImpl();

        Stream<Character> characterStream = code.chars().mapToObj(i -> (char) i);
        Stream<Token> tokenStream = lexer.scan(characterStream);

        Parser parser = new ParserImpl();
        Stream<Statement> statements = parser.parse(tokenStream);

        Interpreter interpreter = new InterpreterImpl();
        Stream<Character> outStream = interpreter.interpret(statements);
        String out = outStream.map(Objects::toString).collect(Collectors.joining());
        assertEquals(expectedOut, out);
    }
    @Test
    public void test004ExampleVariables() {

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

        String expectedOut = """
                5
                Something
                AnotherThing
                true
                """;

        test(code, expectedOut);
    }

}
