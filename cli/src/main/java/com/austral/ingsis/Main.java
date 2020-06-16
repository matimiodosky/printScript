package com.austral.ingsis;

import com.austral.ingsis.impl.LexerImpl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String... args) {
        String code = fileAsString(args[0]);
        LexerImpl lexer = new LexerImpl();
        Parser parser = new ParserImpl();
        Interpreter interpreter = new InterpreterImpl();
        Stream<Character> interpret = interpreter.interpret(parser.parse(lexer.scan(code.chars().mapToObj(i -> (char) i))));
        System.out.println(interpret.map(Object::toString).collect(Collectors.joining()));
    }

    private static String fileAsString(String filePath) {
        try {
            InputStream is = new FileInputStream(filePath);
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
            }
            return sb.toString();
        }catch (Exception e){
            return "";
        }
    }
}
