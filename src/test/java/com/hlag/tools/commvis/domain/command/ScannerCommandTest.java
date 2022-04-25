package com.hlag.tools.commvis.domain.command;

import com.hlag.tools.commvis.service.ExportModelService;
import com.hlag.tools.commvis.service.IEndpointScannerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import picocli.CommandLine;

import static org.assertj.core.api.Assertions.assertThat;

class ScannerCommandTest {
    @Mock
    private IEndpointScannerService scannerService;

    @Mock
    ExportModelService exportModelService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAcceptThePackageName_whenCalledFromCommandLine() throws Exception {
        int actualExitCode = new CommandLine(new ScannerCommand(new IEndpointScannerService[] {scannerService}, exportModelService)).execute("com.hlag");

        assertThat(actualExitCode).isEqualTo(0);
    }

    @Test
    void shouldExitWithStatusCode2_whenCalledFromCommandLine_givenNoPackageName() throws Exception {
        int actualExitCode = new CommandLine(new ScannerCommand(new IEndpointScannerService[] {scannerService}, exportModelService)).execute();

        assertThat(actualExitCode).isEqualTo(2);
    }
}