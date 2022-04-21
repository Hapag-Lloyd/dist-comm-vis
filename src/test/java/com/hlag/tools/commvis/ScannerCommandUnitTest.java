package com.hlag.tools.commvis;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import picocli.CommandLine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ScannerCommandUnitTest {
    @Mock
    private IScannerService scannerService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldAcceptThePackageName_whenCalledFromCommandLine() throws Exception {
        int actualExitCode = new CommandLine(new ScannerCommand(scannerService)).execute("com.hlag");

        assertThat(actualExitCode).isEqualTo(0);
    }

    @Test
    public void shouldExitWithStatusCode2_whenCalledFromCommandLine_givenNoPackageName() throws Exception {
        int actualExitCode = new CommandLine(new ScannerCommand(scannerService)).execute();

        assertThat(actualExitCode).isEqualTo(2);
    }
}