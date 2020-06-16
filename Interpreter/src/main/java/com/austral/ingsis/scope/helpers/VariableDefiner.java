package com.austral.ingsis.scope.helpers;

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

    VariableDefinerImpl withStatement(VariableExplicitDefinitionWithNoValue statement);

    VariableDefinerImpl withResolver(Resolver resolver);


}
