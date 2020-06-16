package com.austral.ingsis;

import com.austral.ingsis.expression.Identifier;
import com.austral.ingsis.expression.LiteralBoolean;
import com.austral.ingsis.expression.LiteralNumber;
import com.austral.ingsis.expression.LiteralString;
import com.austral.ingsis.value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Scope {

    private final VariableDefiner variableDefiner;

    private Stream<Character> out = Stream.empty();

    private final Map<String, Value> values = new HashMap<>();

    public Scope(VariableDefiner variableDefiner) {
        this.variableDefiner = variableDefiner;
    }

    public void append(final Stream<Character> out){
        this.out = Stream.concat(this.out, out);
    }

    public Stream<Character> getOut() {
        return out;
    }

    public void defineVariable(String i, Value v) {
        values.compute(i, variableDefiner);

    }




}
