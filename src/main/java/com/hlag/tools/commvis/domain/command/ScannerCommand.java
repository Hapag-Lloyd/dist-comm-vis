package com.hlag.tools.commvis.domain.command;

import com.hlag.tools.commvis.domain.model.IEndpoint;
import com.hlag.tools.commvis.service.ExportModelService;
import com.hlag.tools.commvis.service.IEndpointScannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

@Slf4j
@Component
@CommandLine.Command(name = "DistCommVis", mixinStandardHelpOptions = true, description = "Analyzes the classpath and extracts endpoints and event sender/receiver.")
public class ScannerCommand implements Callable<Integer> {
    private final IEndpointScannerService[] scannerServices;
    private final ExportModelService exportModelService;

    @CommandLine.Parameters(index = "0", description = "The root package to analyze (including all sub packages)")
    private String rootPackageName;

    public ScannerCommand(IEndpointScannerService[] scannerServices, ExportModelService exportModelService) {
        this.scannerServices = scannerServices;
        this.exportModelService = exportModelService;
    }

    @Override
    public Integer call() throws Exception {
        Set<IEndpoint> endpoints = new HashSet<>();

        Arrays.asList(scannerServices).forEach(s -> endpoints.addAll(s.scanClasspath(rootPackageName)));
        exportModelService.writeJson(endpoints, "model.json");

        log.info(String.valueOf(endpoints));

        return 0;
    }
}
