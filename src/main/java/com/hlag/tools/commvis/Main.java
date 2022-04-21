package com.hlag.tools.commvis;

import picocli.CommandLine;
import java.util.concurrent.Callable;

@CommandLine.Command(name="DistCommVis", mixinStandardHelpOptions = true, description = "Analyzes the classpath and extracts endpoints and event sender/receiver.")
public class Main implements Callable<Integer> {
    public static void main(String... args) {
        int exitCode = new CommandLine(new Main()).execute(args);

        System.exit(exitCode);
    }

    @CommandLine.Parameters(index = "0", description = "The root package to analyze (including all sub packages)")
    private String packageName;

    @Override
    public Integer call() throws Exception {
        System.out.print("The main method");

        return 0;
    }
}
