package com.hlag.tools.commvis.adapter.in;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.assertj.core.api.Assertions.assertThat;

class CombineCommandLineTest {
    private CombineCommandLine commandLine;

    @BeforeEach
    void init() {
        commandLine = new CombineCommandLine();
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