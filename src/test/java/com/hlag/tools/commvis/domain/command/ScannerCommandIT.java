package com.hlag.tools.commvis.domain.command;

import com.hlag.tools.commvis.domain.adapter.PropertyFilesConfiguration;
import com.hlag.tools.commvis.domain.port.out.DotCommunicationModelVisitor;
import com.hlag.tools.commvis.domain.port.out.JsonCommunicationModelVisitor;
import com.hlag.tools.commvis.service.*;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@SpringBootTest(classes = {JaxRsEndpointScannerImpl.class, ScannerCommand.class, ExportModelJsonServiceImpl.class, ExportModelDotServiceImpl.class, PropertyFilesConfiguration.class, JsonCommunicationModelVisitor.class, DotCommunicationModelVisitor.class, JmsEndpointScannerImpl.class})
class ScannerCommandIT {
    @Autowired
    private IEndpointScannerService[] scannerServices;

    @Autowired
    private IExportModelService[] exportModelServices;

    @Value("${git.tags}")
    private String modelVersion;

    @Test
    void shouldMatchCurrentModel_whenWriteJson() throws Exception {
        String expectedJson=contentOf(new File("src/test/resources/model/integration-model.json"));

        new CommandLine(new ScannerCommand(scannerServices, exportModelServices)).execute("integration");

        File actualJsonFile = new File("model.json");

      log.info(contentOf(actualJsonFile));
        assertThat(actualJsonFile).exists().isFile().canRead();
        JSONAssert.assertEquals(expectedJson.replace("###version###", modelVersion), contentOf(actualJsonFile), JSONCompareMode.STRICT);
    }

    @Test
    void shouldMatchCurrentModel_whenWriteDot() {
        String expectedDot=contentOf(new File("src/test/resources/model/integration-model.dot"));

        new CommandLine(new ScannerCommand(scannerServices, exportModelServices)).execute("integration");

        File currentDotFile = new File("model.dot");

        assertThat(currentDotFile).exists().isFile().canRead();
        assertThat(contentOf(currentDotFile)).isEqualTo(expectedDot.replace("\r\n", "\n"));
    }
}