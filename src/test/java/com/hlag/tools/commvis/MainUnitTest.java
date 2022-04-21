package com.hlag.tools.commvis;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainUnitTest {
    private Main main;

    @BeforeEach
    public void init() {
        main = new Main();
    }

    @Test
    public void shouldAcceptThePackageName_whenCalledFromCommandLine() throws Exception {
        int actualExitCode = SystemLambda.catchSystemExit(() -> {
            Main.main("com.hlag.com");
        });

        assertEquals(0, actualExitCode);
    }

    @Test
    public void shouldExitWithStatusCode2_whenCalledFromCommandLine_givenNoPackageName() throws Exception {
        int actualExitCode = SystemLambda.catchSystemExit(() -> {
            Main.main();
        });

        assertEquals(2, actualExitCode);
    }
}