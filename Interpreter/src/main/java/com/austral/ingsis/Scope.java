package com.austral.ingsis;

import com.austral.ingsis.value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Scope {

    private Stream<Character> out = Stream.empty();

    private final Map<String, Value> values = new HashMap<>();

    public void append(final Stream<Character> out){
        this.out = Stream.concat(this.out, out);
    }

    public Stream<Character> getOut() {
        return out;
    }

    public void defineVariable(String i, Value v) {
        values.compute(i, (identifier, value) -> {
            return null;
        });

    }
}
