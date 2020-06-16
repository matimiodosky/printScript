package com.austral.ingsis;

import com.austral.ingsis.statements.VariableDefinition;
import com.austral.ingsis.statements.VariableExplicitDefinition;
import com.austral.ingsis.statements.VariableExplicitDefinitionWithNoValue;
import com.austral.ingsis.value.Value;

import java.util.function.BiFunction;

public interface VariableDefiner extends BiFunction<String, Value, Value> {

    @Override
    Value apply(String s, Value value);

    VariableDefinerImpl withStatement(VariableExplicitDefinition statement);

    VariableDefinerImpl withStatement(VariableDefinition statement);

    public VariableDefinerImpl withStatement(VariableExplicitDefinitionWithNoValue statement);

    public VariableDefinerImpl withResolver(Resolver resolver);


}
