package com.austral.ingsis;


import com.austral.ingsis.impl.LexerImpl;
import com.austral.ingsis.impl.SyntaxError;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OptionalFeaturesLexerTest {


    @Test(expected = SyntaxError.class)
    public void test01DisabledTokenResultsInError() {

        String code = """
                    let a = true;
                """;
        Lexer lexer = new LexerImpl();
        lexer.scan(code.chars().mapToObj(i -> (char) i));
    }

    @Test
    public void test02EnabledTokenResultsInSuccess() {

        String code = """
                    let a = true;
                """;
        Lexer lexer = new LexerImpl();
        lexer.scan(
                code.chars().mapToObj(i -> (char) i),
                Collections.singletonList(TokenType.TRUELITERAL)
        );
    }


}
