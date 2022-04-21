package com.hlag.tools.commvis;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import picocli.CommandLine;

import java.util.concurrent.Callable;

import static org.reflections.scanners.Scanners.SubTypes;

@Slf4j
@CommandLine.Command(name = "DistCommVis", mixinStandardHelpOptions = true, description = "Analyzes the classpath and extracts endpoints and event sender/receiver.")
public class Main implements Callable<Integer> {
    @CommandLine.Parameters(index = "0", description = "The root package to analyze (including all sub packages)")
    private String packageName;

    public static void main(String... args) {
        int exitCode = new CommandLine(new Main()).execute(args);

        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        log.info("The main method");

        new Reflections(packageName);

        return 0;
    }
}
