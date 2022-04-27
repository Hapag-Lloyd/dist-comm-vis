package com.hlag.tools.commvis.domain.command;

import com.hlag.tools.commvis.domain.model.CommunicationModel;
import com.hlag.tools.commvis.domain.model.IEndpoint;
import com.hlag.tools.commvis.service.ExportModelJsonServiceImpl;
import com.hlag.tools.commvis.service.IEndpointScannerService;
import com.hlag.tools.commvis.service.IExportModelService;
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
    private final IExportModelService[] exportModelServices;

    @CommandLine.Parameters(index = "0", description = "The root package to analyze (including all sub packages)")
    private String rootPackageName;

    public ScannerCommand(IEndpointScannerService[] scannerServices, IExportModelService[] exportModelServices) {
        this.scannerServices = scannerServices;
        this.exportModelServices = exportModelServices;
    }

    @Override
    public Integer call() throws Exception {
        Set<IEndpoint> endpoints = new HashSet<>();
        Arrays.asList(scannerServices).forEach(s -> endpoints.addAll(s.scanClasspath(rootPackageName)));

        CommunicationModel model = new CommunicationModel(endpoints);
        Arrays.asList(exportModelServices).forEach(s -> s.export(model, "model"));

        log.info(String.valueOf(endpoints));

        return 0;
    }
}
