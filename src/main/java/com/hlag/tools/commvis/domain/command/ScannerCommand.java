package com.hlag.tools.commvis.domain.command;

import com.hlag.tools.commvis.domain.model.CommunicationModel;
import com.hlag.tools.commvis.domain.model.IEndpoint;
import com.hlag.tools.commvis.service.IEndpointScannerService;
import com.hlag.tools.commvis.service.IExportModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.Callable;

@Slf4j
@Component
@CommandLine.Command(name = "DistCommVis", mixinStandardHelpOptions = true, description = "Analyzes the classpath and extracts endpoints and event sender/receiver.")
public class ScannerCommand implements Callable<Integer> {
    private final IEndpointScannerService[] scannerServices;
    private final IExportModelService[] exportModelServices;

    @CommandLine.Parameters(index = "1", description = "The root package to analyze (including all sub packages)")
    private String rootPackageName;

    @CommandLine.Parameters(index = "0", description = "ID of the project to be analyzed. Used to link projects.")
    private String projectId;

    @CommandLine.Option(names = {"-n", "--name"}, defaultValue = "application", description = "Name of the project to be analyzed.")
    private String projectName;

    public ScannerCommand(IEndpointScannerService[] scannerServices, IExportModelService[] exportModelServices) {
        this.scannerServices = scannerServices;
        this.exportModelServices = exportModelServices;
    }

    @Override
    public Integer call() {
        Collection<IEndpoint> endpoints = new HashSet<>();
        Arrays.asList(scannerServices).forEach(s -> endpoints.addAll(s.scanClasspath(rootPackageName)));

        CommunicationModel model = new CommunicationModel(projectId, projectName, endpoints);
        Arrays.asList(exportModelServices).forEach(s -> s.export(model, "model"));

        log.info(String.valueOf(endpoints));

        return 0;
    }
}
