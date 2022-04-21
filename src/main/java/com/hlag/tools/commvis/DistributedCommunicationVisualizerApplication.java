package com.hlag.tools.commvis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;

@SpringBootApplication
public class DistributedCommunicationVisualizerApplication implements CommandLineRunner, ExitCodeGenerator {
    private CommandLine.IFactory factory;
    private ScannerCommand scannerCommand;

    public DistributedCommunicationVisualizerApplication(CommandLine.IFactory factory, ScannerCommand scannerCommand) {
        this.factory = factory;
        this.scannerCommand = scannerCommand;
    }

    private int exitCode;

    public static void main(String... args) {
        // let Spring instantiate and inject dependencies
        System.exit(SpringApplication.exit(SpringApplication.run(DistributedCommunicationVisualizerApplication.class, args)));
    }

    @Override
    public void run(String... args) throws Exception {
        // let picocli parse command line args and run the business logic
        exitCode = new CommandLine(scannerCommand, factory).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
