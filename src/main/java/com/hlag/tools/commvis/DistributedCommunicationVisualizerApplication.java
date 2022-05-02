package com.hlag.tools.commvis;

import com.hlag.tools.commvis.adapter.in.CombineCommandLine;
import com.hlag.tools.commvis.adapter.in.ScanCommandLine;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;

@RequiredArgsConstructor
@SpringBootApplication
@CommandLine.Command(subcommands = {ScanCommandLine.class, CombineCommandLine.class}, mixinStandardHelpOptions = true)
public class DistributedCommunicationVisualizerApplication implements CommandLineRunner, ExitCodeGenerator {
    private final CommandLine.IFactory factory;
    private int exitCode;

    public static void main(String... args) {
        // let Spring instantiate and inject dependencies
        System.exit(SpringApplication.exit(SpringApplication.run(DistributedCommunicationVisualizerApplication.class, args)));
    }

    @Override
    public void run(String... args) throws Exception {
        // let picocli parse command line args and run the business logic
        exitCode = new CommandLine(this, factory).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
