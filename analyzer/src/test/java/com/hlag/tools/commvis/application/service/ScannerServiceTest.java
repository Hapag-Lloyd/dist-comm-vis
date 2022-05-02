package com.hlag.tools.commvis.application.service;

import com.hlag.tools.commvis.analyzer.service.IScannerService;
import com.hlag.tools.commvis.application.port.in.ScannerCommand;
import com.hlag.tools.commvis.domain.service.IExportModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ScannerServiceTest {
    @Mock
    private IScannerService scanner1;
    @Mock
    private IScannerService scanner2;
    @Mock
    private IExportModelService exportModelService1;
    @Mock
    private IExportModelService exportModelService2;

    private ScannerService scannerService;
    private ScannerCommand command = new ScannerCommand("com.hlag", "4711", "my-project-name");

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        scannerService = new ScannerService(new IScannerService[]{scanner1, scanner2}, new IExportModelService[]{exportModelService1, exportModelService2});
    }

    @Test
    void shouldCallAllScanners_whenScanSenderReceiverAndExport() {
        scannerService.scanSenderReceiverAndExport(command);

        Mockito.verify(scanner1).scanSenderAndReceiver(command.getRootPackage());
        Mockito.verify(scanner2).scanSenderAndReceiver(command.getRootPackage());
    }

    @Test
    void shouldCallAllExporters_whenScanSenderReceiverAndExport() {
        scannerService.scanSenderReceiverAndExport(command);

        Mockito.verify(exportModelService1).export(Mockito.any(), Mockito.any());
        Mockito.verify(exportModelService2).export(Mockito.any(), Mockito.any());
    }
}