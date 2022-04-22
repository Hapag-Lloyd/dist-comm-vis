package com.hlag.tools.commvis.domain.command;

import com.hlag.tools.commvis.service.IScannerService;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@Component
@CommandLine.Command(name = "DistCommVis", mixinStandardHelpOptions = true, description = "Analyzes the classpath and extracts endpoints and event sender/receiver.")
public class ScannerCommand implements Callable<Integer> {
    private final IScannerService scannerService;

    @CommandLine.Parameters(index = "0", description = "The root package to analyze (including all sub packages)")
    private String rootPackageName;

    public ScannerCommand(IScannerService scannerService) {
        this.scannerService = scannerService;
    }

    @Override
    public Integer call() throws Exception {
        scannerService.scanClasspath(rootPackageName);

        return 0;
    }
}
