package com.austral.ingsis;

import com.austral.ingsis.impl.LexerImpl;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

@Command(name = "printscriot", mixinStandardHelpOptions = true, version = "printscript 1.0")
class CLI implements Callable<Integer> {

    @Parameters(index = "0", description = "Code")
    private File file;

    @Option(names = {"-v", "--validate"}, description = "Enters in VALIDATION ONLY mode")
    private  boolean onlyValidate = false;

    @Option(names = {"-b", "--boolean"}, description = "Activates boolean feature")
    private  boolean booleanActive = false;

    @Option(names = {"-c", "--const"}, description = "Activates const feature")
    private  boolean constActive = false;


    public static void main(String... args) {
        int exitCode = new CommandLine(new CLI()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        try {

            Stream<Character> code = new BufferedReader(new FileReader(file))
                    .lines()
                    .flatMap(line -> line.chars().mapToObj(i -> (char) i));

            Lexer lexer = new LexerImpl();


            List<TokenType> enabledTokens = new ArrayList<>();
            if (booleanActive)
                enabledTokens.addAll(Arrays.asList(
                        TokenType.BOOLEANTYPE,
                        TokenType.TRUELITERAL,
                        TokenType.FALSELITERAL,
                        TokenType.GRATEREQUAL,
                        TokenType.LESSEQUAL,
                        TokenType.GRATER,
                        TokenType.LESS
                ));
            if (constActive) {
                enabledTokens.addAll(Arrays.asList(
                        TokenType.CONST
                ));
            }

            Stream<Token> scan = lexer.scan(code, enabledTokens);

            Parser parser = new ParserImpl();

            Stream<Statement> statements = parser.parse(scan);

            if (onlyValidate) {
                return 0;
            }

            Interpreter interpreter = new InterpreterImpl();

            Stream<Character> out = interpreter.interpret(statements);

            out.forEach(System.out::print);

            return 0;

        }catch (RuntimeException e){
            System.err.println(e.getMessage());
            return  -1;
        }
        catch (Exception e){
            System.err.println("Unknown error");
            return  -1;
        }
    }


}
