package com.hlag.tools.commvis.adapter.in;

import com.hlag.tools.commvis.application.port.in.ScannerCommand;
import com.hlag.tools.commvis.application.port.in.ScannerUseCase;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@RequiredArgsConstructor
@CommandLine.Command(name = "scan", aliases = {"s"}, description = "Analyzes the classpath and extracts endpoints and event sender/receiver.")
public class ScanCommandLine implements Callable<Integer> {
    private final ScannerUseCase scannerUseCase;

    @CommandLine.Parameters(index = "0", description = "The root package to analyze (including all sub packages)")
    private String rootPackageName;

    @CommandLine.Parameters(index = "1", description = "ID of the project to be analyzed. Used to link projects.")
    private String projectId;

    @CommandLine.Option(names = {"-n", "--name"}, defaultValue = "application", description = "Name of the project to be analyzed.")
    private String projectName;

    @Override
    public Integer call() {
        ScannerCommand command = new ScannerCommand(rootPackageName, projectId, projectName);

        scannerUseCase.scanSenderReceiverAndExport(command);

        return 0;
    }
}
