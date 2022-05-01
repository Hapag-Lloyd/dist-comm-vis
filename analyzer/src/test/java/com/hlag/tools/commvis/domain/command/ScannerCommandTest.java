package com.hlag.tools.commvis.domain.command;

import com.hlag.tools.commvis.analyzer.service.IScannerService;
import com.hlag.tools.commvis.service.IExportModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import picocli.CommandLine;

import static org.assertj.core.api.Assertions.assertThat;

class ScannerCommandTest {
    @Mock
    IExportModelService exportModelServices;
    @Mock
    private IScannerService scannerService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAcceptProjectIdAndPackageName_whenCalledFromCommandLine() {
        int actualExitCode = new CommandLine(new ScannerCommand(new IScannerService[]{scannerService}, new IExportModelService[]{exportModelServices})).execute("4711", "com.hlag");

        assertThat(actualExitCode).isZero();
    }

    @Test
    void shouldExitWithStatusCode2_whenCalledFromCommandLine_givenNoParameters() {
        int actualExitCode = new CommandLine(new ScannerCommand(new IScannerService[]{scannerService}, new IExportModelService[]{exportModelServices})).execute();

        assertThat(actualExitCode).isEqualTo(2);
    }

    @Test
    void shouldExitWithStatusCode2_whenCalledFromCommandLine_givenProjectIdOnly() {
        int actualExitCode = new CommandLine(new ScannerCommand(new IScannerService[]{scannerService}, new IExportModelService[]{exportModelServices})).execute("4711");

        assertThat(actualExitCode).isEqualTo(2);
    }
}