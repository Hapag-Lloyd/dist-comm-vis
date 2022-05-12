package com.hlag.tools.commvis.application.service;

import com.hlag.tools.commvis.analyzer.model.CommunicationModel;
import com.hlag.tools.commvis.analyzer.model.ISenderReceiverCommunication;
import com.hlag.tools.commvis.analyzer.service.IScannerService;
import com.hlag.tools.commvis.application.port.in.ScannerCommand;
import com.hlag.tools.commvis.application.port.in.ScannerUseCase;
import com.hlag.tools.commvis.domain.service.IExportModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ScannerService implements ScannerUseCase {
    private final IScannerService[] scannerServices;
    private final IExportModelService[] exportModelServices;

    @Override
    public void scanSenderReceiverAndExport(ScannerCommand command) {
        Collection<ISenderReceiverCommunication> endpoints = new HashSet<>();
        Arrays.asList(scannerServices).forEach(s -> endpoints.addAll(s.scanSenderAndReceiver(command.getRootPackage())));

        CommunicationModel model = new CommunicationModel(command.getProjectId(), command.getProjectName(), endpoints);

        String fileName = Optional.ofNullable(command.getProjectName()).map(pn -> String.format("model-%s", pn)).orElse("model");
        Arrays.asList(exportModelServices).forEach(s -> s.export(model, fileName));
    }
}
