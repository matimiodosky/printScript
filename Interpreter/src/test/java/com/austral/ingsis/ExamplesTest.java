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

        Interpreter interpreter = new InterpreterImpl(in -> parser.parse(lexer.scan(in)));

        Stream<Character> outStream = interpreter.interpret(statements);
        String out = outStream.map(Objects::toString).collect(Collectors.joining());
        assertEquals(expectedOut, out);
    }
    @Test
    public void test001ExampleVariables() {

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

    @Test
    public void test002ExampleVariablesWithout() {

        String code = """
                let y: string;
                let x: number;
                let z: boolean;
                                
                print(x);
                print(y);
                print(z);
                                
                """;

        String expectedOut = """
                null
                null
                null
                """;

        test(code, expectedOut);
    }

    @Test
    public void test003ExampleVariablesReAssigned() {

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

        String expectedOut = """
                5
                AnotherThing
                18
                SomethingElse
                """;

        test(code, expectedOut);
    }

    @Test
    public void test004ExampleSum() {

        String code = """
                let x: number = 1 + 1;        
                print(x);  
                """;

        String expectedOut = """
                2
                """;

        test(code, expectedOut);
    }

    @Test
    public void test005IFExample() {

        String code = """
                if(true){
                    print("Hello");
                };
                if(false)}
                    print("World");
                };
                """;

        String expectedOut = """
                Hello
                """;

        test(code, expectedOut);
    }

    @Test
    public void test006Import() {

        String code = """
               import "/Users/matiasmiodosky/projects/austral/ing/printScript/Interpreter/src/test/resources/test.ts";
                """;

        String expectedOut = """
                Hello World!!
                """;

        test(code, expectedOut);
    }

}
