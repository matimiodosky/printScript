package com.austral.ingsis;

import com.austral.ingsis.commands.Version;
import picocli.CommandLine;

public class Main {

    public static void main(String... args) {
        int exitCode = new CommandLine(new Version()).execute(args);
    }
}
