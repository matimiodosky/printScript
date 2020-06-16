package com.austral.ingsis.commands;

import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "version", mixinStandardHelpOptions = true, version = "version 0.0.1",
        description = "Prints the current version")
public class Version implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("0.0.1");
        return "0.0.1";
    }
}
