package com.austral.ingsis;

import com.austral.ingsis.scope.Scope;
import com.austral.ingsis.scope.helpers.variable.VariableAssignerImpl;
import com.austral.ingsis.scope.helpers.variable.VariableDefinerImpl;
import com.austral.ingsis.statements.*;
import com.austral.ingsis.value.Value;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class InterpreterImpl implements Interpreter {


    private final ProgramParser programParser;

    public InterpreterImpl(ProgramParser programParser) {
        this.programParser = programParser;
    }

    public InterpreterImpl() {
        this.programParser = null;
    }

    @Override
    public Stream<Character> interpret(Stream<Statement> statements) {

        Scope scope = new Scope(new VariableDefinerImpl(), new VariableAssignerImpl());
        statements.forEachOrdered(statement -> interpret(scope, statement));
        return scope.getOut();
    }

    private void interpret(Scope scope, Statement statement) {
        if (statement instanceof Print print) {
            interpret(scope, print);
        } else if (statement instanceof Import imp) {
            interpret(scope, imp);
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
        } else if (statement instanceof IfElse ifElsestatement) {
            interpret(scope, ifElsestatement);
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

    private void interpret(Scope scope, Import statement) {
        if (programParser == null) throw new RuntimeException("No program parser");
        Value path = scope.resolve(statement.getPath());
        if (!path.isString()) throw new RuntimeException("Invalid path");
        String code = fileAsString(path
                .getAsString()
                .orElseThrow(() -> new RuntimeException("Invalid path"))
                .getValue()
        );
        Stream<Statement> statementStream = this.programParser.parse(code.chars().mapToObj(i -> (char) i));
        scope.append(interpret(statementStream));
    }

    private void interpret(Scope scope, If statement) {
        Value condition = scope.resolve(statement.getCondition());
        if (condition.getAsBoolean().orElseThrow(() -> new RuntimeException("Condition must be boolean")).getValue()) {
            statement.getInnerStatements().forEach(inner -> this.interpret(scope, inner));
        }
    }

    private void interpret(Scope scope, IfElse statement) {
        if (scope.resolve(statement.getCondition()).getAsBoolean().orElseThrow(() -> new RuntimeException("Condition must be boolean")).getValue()){
            statement.getIfStatements().forEach(inner -> this.interpret(scope, inner));
        }else{
            statement.getElseStatements().forEach(inner -> this.interpret(scope, inner));
        }

    }

    private Stream<Character> toStream(Object object) {
        return object
                .toString()
                .chars()
                .mapToObj(i -> (char) i);
    }

    private static String fileAsString(String filePath) {
        try {
            InputStream is = new FileInputStream(filePath);
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Could not read file");
        }
    }

}
