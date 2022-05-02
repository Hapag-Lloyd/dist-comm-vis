package com.hlag.tools.commvis.adapter.in;

import com.hlag.tools.commvis.application.port.in.ScannerUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import picocli.CommandLine;

import static org.assertj.core.api.Assertions.assertThat;

class ScanCommandLineTest {
    @Mock
    private ScannerUseCase scannerUseCase;

    private ScanCommandLine commandLine;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        commandLine = new ScanCommandLine(scannerUseCase);
    }

    @Test
    void shouldReturn0_whenCall_givenNoErrorOccurred() {
        Integer actualExitCode = commandLine.call();

        Assertions.assertThat(actualExitCode).isZero();
    }

    @Test
    void shouldAcceptProjectIdAndPackageName_whenCalledFromCommandLine() {
        int actualExitCode = new CommandLine(commandLine).execute("com.hlag", "4711");

        assertThat(actualExitCode).isZero();
    }

    @Test
    void shouldExitWithStatusCode2_whenCalledFromCommandLine_givenNoParameters() {
        int actualExitCode = new CommandLine(commandLine).execute();

        assertThat(actualExitCode).isEqualTo(2);
    }

    @Test
    void shouldExitWithStatusCode2_whenCalledFromCommandLine_givenRootPackageOnly() {
        int actualExitCode = new CommandLine(commandLine).execute("com.hlag");

        assertThat(actualExitCode).isEqualTo(2);
    }

    @Test
    void shouldAcceptANameForTheModel_whenCalledFromCommandLine() {
        int actualExitCode = new CommandLine(commandLine).execute("com.hlag", "4711", "-n", "a-fancy-name");

        assertThat(actualExitCode).isZero();
    }
}