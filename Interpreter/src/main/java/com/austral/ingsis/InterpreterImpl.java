package com.austral.ingsis;

import com.austral.ingsis.statements.Print;

import java.util.stream.Stream;

public class InterpreterImpl implements Interpreter {

    @Override
    public Stream<Character> interpret(Stream<Statement> statements) {
        return statements
                .flatMap(this::interpret);
    }

    private Stream<Character> interpret(Statement statement){
            if (statement instanceof Print print){
                return toStream(print.getValue().getValue());
            }
            throw new RuntimeException("Not implemented: "+ statement.getClass().getName());
    }

    private Stream<Character> toStream(Object object){
        return object.toString().chars().mapToObj(i -> (char) i);
    }
}
