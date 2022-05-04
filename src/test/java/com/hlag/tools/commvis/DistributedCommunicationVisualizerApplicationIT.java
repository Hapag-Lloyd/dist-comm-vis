package com.hlag.tools.commvis;

import com.hlag.tools.commvis.adapter.in.ScanCommandLine;
import com.hlag.tools.commvis.application.port.in.ScannerUseCase;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import picocli.CommandLine;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

@SpringBootTest
class DistributedCommunicationVisualizerApplicationIT {
    @Autowired
    private ScannerUseCase scannerUseCase;

    @Value("${git.tags}")
    private String modelVersion;

    @Test
    void shouldMatchCurrentJsonModel_whenScan() throws Exception {
        String expectedJson = contentOf(new File("src/test/resources/model/integration-model.json"));

        new CommandLine(new ScanCommandLine(scannerUseCase)).execute("--name=my-project", "integration", "4711");

        File actualJsonFile = new File("model.json");

        assertThat(actualJsonFile).exists().isFile().canRead();
        JSONAssert.assertEquals(expectedJson.replace("###version###", modelVersion), contentOf(actualJsonFile), JSONCompareMode.STRICT);
    }

    @Test
    void shouldMatchCurrentDotModel_whenScan() {
        String expectedDot = contentOf(new File("src/test/resources/model/integration-model.dot"));

        new CommandLine(new ScanCommandLine(scannerUseCase)).execute("-n", "my-project", "integration", "4711");

        File currentDotFile = new File("model.dot");

        assertThat(currentDotFile).exists().isFile().canRead();
        assertThat(contentOf(currentDotFile)).isEqualTo(expectedDot);
    }
}