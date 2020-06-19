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
        Stream<Token> scan = lexer.scan(code.chars().mapToObj(i -> (char) i));
        Parser parser = new ParserImpl();
        Stream<Statement> parse = parser.parse(scan);
        Interpreter interpreter = new InterpreterImpl(in -> parser.parse(lexer.scan(in)));
        Stream<Character> interpret = interpreter.interpret(parse);
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
