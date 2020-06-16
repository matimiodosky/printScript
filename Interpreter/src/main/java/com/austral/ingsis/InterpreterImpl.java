package com.austral.ingsis;

import com.austral.ingsis.scope.Scope;
import com.austral.ingsis.scope.helpers.variable.VariableAssignerImpl;
import com.austral.ingsis.scope.helpers.variable.VariableDefinerImpl;
import com.austral.ingsis.statements.*;
import com.austral.ingsis.value.Value;

import java.util.stream.Stream;

public class InterpreterImpl implements Interpreter {


    @Override
    public Stream<Character> interpret(Stream<Statement> statements) {

        Scope scope = new Scope(new VariableDefinerImpl(), new VariableAssignerImpl());
        statements.forEachOrdered(statement -> interpret(scope, statement));
        return scope.getOut();
    }

    private void interpret(Scope scope, Statement statement) {
        if (statement instanceof Print print) {
            interpret(scope, print);
        } else if (statement instanceof VariableExplicitDefinition variableExplicitDefinition) {
            interpret(scope, variableExplicitDefinition);
        } else if (statement instanceof VariableExplicitDefinitionWithNoValue variableDefinitionWithNoValue) {
            interpret(scope, variableDefinitionWithNoValue);
        } else if (statement instanceof VariableDefinition variableDefinition) {
            interpret(scope, variableDefinition);
        } else if (statement instanceof VariableAssignment variableAssignment) {
            interpret(scope, variableAssignment);
        } else if (statement instanceof If ifstatement) {
            interpret(scope, ifstatement);
        } else throw new RuntimeException("Not implemented: " + statement.getClass().getName());
    }

    private void interpret(Scope scope, Print statement) {
        scope.append(
                this.toStream(
                        scope.resolve(statement.getExpression()).toString() + "\n"
                )
        );
    }

    private void interpret(Scope scope, VariableExplicitDefinition statement) {
        scope.defineVariable(statement);
    }

    private void interpret(Scope scope, VariableDefinition statement) {
        scope.defineVariable(statement);
    }

    private void interpret(Scope scope, VariableExplicitDefinitionWithNoValue statement) {
        scope.defineVariable(statement);
    }

    private void interpret(Scope scope, VariableAssignment statement) {
        scope.assign(statement);
    }

    private void interpret(Scope scope, If statement) {
        Value condition = scope.resolve(statement.getCondition());
        if (!condition.isBoolean()) throw new RuntimeException("condition should be boolean");
        if (condition.getAsBoolean().getValue()) {
            statement.getInnerStatements().forEach(inner -> this.interpret(scope, inner));
        }
    }

    private Stream<Character> toStream(Object object) {
        return object
                .toString()
                .chars()
                .mapToObj(i -> (char) i);
    }
}
