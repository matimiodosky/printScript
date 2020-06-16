package com.austral.ingsis;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Scope {

    private Stream<Character> out = Stream.empty();

    private final Map<String, Type> types = new HashMap<>();

    public void append(final Stream<Character> out){
        this.out = Stream.concat(this.out, out);
    }

    public Stream<Character> getOut() {
        return out;
    }
}
