package com.hlag.tools.commvis.adapter.in;

import com.hlag.tools.commvis.application.port.in.ScannerUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import picocli.CommandLine;

import static org.assertj.core.api.Assertions.assertThat;

class ScanSenderReceiverCommandLineTest {
    @Mock
    private ScannerUseCase scannerUseCase;

    private ScanSenderReceiverCommandLine controller;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        controller = new ScanSenderReceiverCommandLine(scannerUseCase);
    }

    @Test
    void shouldReturn0_whenCall_givenNoErrorOccurred() {
        Integer actualExitCode = controller.call();

        Assertions.assertThat(actualExitCode).isZero();
    }

    @Test
    void shouldAcceptProjectIdAndPackageName_whenCalledFromCommandLine() {
        int actualExitCode = new CommandLine(new ScanSenderReceiverCommandLine(scannerUseCase)).execute("4711", "com.hlag");

        assertThat(actualExitCode).isZero();
    }

    @Test
    void shouldExitWithStatusCode2_whenCalledFromCommandLine_givenNoParameters() {
        int actualExitCode = new CommandLine(new ScanSenderReceiverCommandLine(scannerUseCase)).execute();

        assertThat(actualExitCode).isEqualTo(2);
    }

    @Test
    void shouldExitWithStatusCode2_whenCalledFromCommandLine_givenProjectIdOnly() {
        int actualExitCode = new CommandLine(new ScanSenderReceiverCommandLine(scannerUseCase)).execute("4711");

        assertThat(actualExitCode).isEqualTo(2);
    }
}