package com.hlag.tools.commvis.domain.command;

import com.hlag.tools.commvis.service.IEndpointScannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.concurrent.Callable;

@Slf4j
@Component
@CommandLine.Command(name = "DistCommVis", mixinStandardHelpOptions = true, description = "Analyzes the classpath and extracts endpoints and event sender/receiver.")
public class ScannerCommand implements Callable<Integer> {
    private final IEndpointScannerService[] scannerServices;

    @CommandLine.Parameters(index = "0", description = "The root package to analyze (including all sub packages)")
    private String rootPackageName;

    public ScannerCommand(IEndpointScannerService[] scannerServices) {
        this.scannerServices = scannerServices;
    }

    @Override
    public Integer call() throws Exception {
        Arrays.asList(scannerServices).forEach(s -> log.info(String.valueOf(s.scanClasspath(rootPackageName))));

        return 0;
    }
}
