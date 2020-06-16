package com.austral.ingsis;

import com.austral.ingsis.statements.VariableAssignment;
import com.austral.ingsis.statements.VariableDefinition;
import com.austral.ingsis.statements.VariableExplicitDefinition;
import com.austral.ingsis.statements.VariableExplicitDefinitionWithNoValue;
import com.austral.ingsis.value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Scope {


    private final VariableDefiner variableDefiner;
    private final VariableAssigner variableAssigner;
    private final Resolver resolver;
    private Stream<Character> out = Stream.empty();

    private final Map<String, Value> values = new HashMap<>();

    public Scope(VariableDefiner variableDefiner, VariableAssigner variableAssigner) {
        resolver = new ResolverImpl(this);
        this.variableDefiner = variableDefiner.withResolver(resolver);
        this.variableAssigner = variableAssigner.withResolver(resolver);
    }

    public void append(final Stream<Character> out){
        this.out = Stream.concat(this.out, out);
    }

    public Stream<Character> getOut() {
        return out;
    }

    public void defineVariable(VariableExplicitDefinition statement) {
        values.compute(statement.getIdentifier(),variableDefiner.withStatement(statement));
    }

    public void defineVariable(VariableExplicitDefinitionWithNoValue statement) {
        values.compute(statement.getIdentifier(), variableDefiner.withStatement(statement));
    }

    public void defineVariable(VariableDefinition statement) {
        values.compute(statement.getIdentifier(), variableDefiner.withStatement(statement));
    }


    public Value getVariableValue(String n) {
        return values.compute(n, (name, value) -> {
            if (value == null)throw new RuntimeException("No such variable");
            return value;
        });
    }

    public Value resolve(Expression expression) {
        return resolver.resolve(expression);
    }

    public void assign(VariableAssignment statement) {
        values.compute(statement.getIdentifier(), variableAssigner.withStatement(statement));
    }
}
