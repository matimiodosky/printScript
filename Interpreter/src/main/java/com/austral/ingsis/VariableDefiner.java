package com.austral.ingsis;

import com.austral.ingsis.value.Value;

import java.util.function.BiFunction;

public interface VariableDefiner extends BiFunction<String, Value, Value> {

    @Override
     Value apply(String s, Value value);
}
