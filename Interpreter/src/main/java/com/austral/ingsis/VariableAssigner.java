package com.austral.ingsis;

import com.austral.ingsis.statements.VariableAssignment;
import com.austral.ingsis.statements.VariableDefinition;
import com.austral.ingsis.statements.VariableExplicitDefinition;
import com.austral.ingsis.statements.VariableExplicitDefinitionWithNoValue;
import com.austral.ingsis.value.Value;

import java.util.function.BiFunction;

public interface VariableAssigner extends BiFunction<String, Value, Value> {



    @Override
    Value apply(String s, Value value);

    VariableAssigner withStatement(VariableAssignment statement);

    VariableAssigner withResolver(Resolver resolver);


}
