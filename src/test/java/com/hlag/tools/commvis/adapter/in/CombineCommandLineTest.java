package com.hlag.tools.commvis.adapter.in;

import com.hlag.tools.commvis.application.port.in.CombineUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import picocli.CommandLine;

import static org.assertj.core.api.Assertions.assertThat;

class CombineCommandLineTest {
    private CombineCommandLine commandLine;

    @Mock
    private CombineUseCase combineUseCase;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        commandLine = new CombineCommandLine(combineUseCase);
    }

    @Test
    void shouldAcceptTopLevelDirectory_whenCalledFromCommandLine() {
        int actualExitCode = new CommandLine(commandLine).execute("/tmp/path");

        assertThat(actualExitCode).isZero();
    }

    @Test
    void shouldReturn0_whenCall_givenNoErrorOccurred() {
        Integer actualExitCode = commandLine.call();

        Assertions.assertThat(actualExitCode).isZero();
    }

    @Test
    void shouldExitWithStatusCode2_whenCalledFromCommandLine_givenNoParameters() {
        int actualExitCode = new CommandLine(commandLine).execute();

        assertThat(actualExitCode).isEqualTo(2);
    }

    @Test
    void shouldAcceptANameForTheModel_whenCalledFromCommandLine() {
        int actualExitCode = new CommandLine(commandLine).execute("/tmp/path", "-n", "a-fancy-name");

        assertThat(actualExitCode).isZero();
    }
}