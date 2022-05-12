package com.hlag.tools.commvis.application.service;

import com.hlag.tools.commvis.analyzer.model.CommunicationModel;
import com.hlag.tools.commvis.analyzer.service.IScannerService;
import com.hlag.tools.commvis.application.port.in.ScannerCommand;
import com.hlag.tools.commvis.application.port.in.ScannerUseCase;
import com.hlag.tools.commvis.domain.service.IExportModelService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class ScannerService implements ScannerUseCase {
    private final IScannerService[] scannerServices;
    private final IExportModelService[] exportModelServices;
    private final String version;

    public ScannerService(IScannerService[] scannerServices, IExportModelService[] exportModelServices, @Value("${git.tags}") String version) {
        this.scannerServices = scannerServices;
        this.exportModelServices = exportModelServices;
        this.version = version;
    }

    @Override
    public void scanSenderReceiverAndExport(ScannerCommand command) {
        CommunicationModel model = new CommunicationModel(command.getProjectId(), command.getProjectName(), version);

        Arrays.asList(scannerServices).forEach(s -> {
            s.scanSenderAndReceiver(command.getRootPackage()).forEach(model::addSenderReceiver);
        });

        String fileName = Optional.ofNullable(command.getProjectName()).map(pn -> String.format("model-%s", pn)).orElse("model");
        Arrays.asList(exportModelServices).forEach(s -> s.export(model, fileName));
    }
}
