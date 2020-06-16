package com.austral.ingsis;

import com.austral.ingsis.statements.*;

import java.util.stream.Stream;

public class InterpreterImpl implements Interpreter {

    @Override
    public Stream<Character> interpret(Stream<Statement> statements) {

        Scope scope = new Scope();
        statements.forEachOrdered(statement -> interpret(scope, statement));
        return scope.getOut();
    }

    private void interpret(Scope scope, Statement statement) {
        if (statement instanceof Print print) {
            interpret(scope, print);
        } else if (statement instanceof VariableDefinition variableDefinition) {
            interpret(scope, variableDefinition);
        } else if (statement instanceof VariableExplicitDefinitionWithNoValue variableDefinitionWithNoValue) {
            interpret(scope, variableDefinitionWithNoValue);
        } else if (statement instanceof VariableExplicitDefinition variableExplicitDefinition) {
            interpret(scope, variableExplicitDefinition);
        } else if (statement instanceof VariableAssignment variableAssignment) {
            interpret(scope, variableAssignment);
        }else throw new RuntimeException("Not implemented: " + statement.getClass().getName());
    }

    private void interpret(Scope scope, Print statement) {
        scope.append(this.toStream(statement.getValue().getValue()));
    }

    private void interpret(Scope scope, VariableDefinition statement) {
        throw new RuntimeException("Not implemented: " + statement.getClass().getName());
    }

    private void interpret(Scope scope, VariableExplicitDefinitionWithNoValue statement) {
        throw new RuntimeException("Not implemented: " + statement.getClass().getName());
    }

    private void interpret(Scope scope, VariableExplicitDefinition statement) {
        throw new RuntimeException("Not implemented: " + statement.getClass().getName());
    }

    private void interpret(Scope scope, VariableAssignment statement) {
        throw new RuntimeException("Not implemented: " + statement.getClass().getName());
    }

    private Stream<Character> toStream(Object object) {
        return object.toString().chars().mapToObj(i -> (char) i);
    }
}