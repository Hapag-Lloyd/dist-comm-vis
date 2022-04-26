package com.hlag.tools.commvis.domain.command;

import com.hlag.tools.commvis.domain.adapter.ObjectMapperConfiguration;
import com.hlag.tools.commvis.domain.adapter.PropertyFilesConfiguration;
import com.hlag.tools.commvis.service.*;
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

@SpringBootTest(classes = {JaxRsEndpointScannerImpl.class, ScannerCommand.class, ExportModelJsonServiceImpl.class, ExportModelDotServiceImpl.class, ObjectMapperConfiguration.class, PropertyFilesConfiguration.class})
class ScannerCommandIT {
    @Autowired
    private IEndpointScannerService scannerService;

    @Autowired
    private IExportModelService[] exportModelServices;

    @Value("${git.tags}")
    private String modelVersion;

    @Test
    void shouldMatchCurrentModel_whenWriteJson() throws Exception {
        String expectedJson=contentOf(new File("src/test/resources/model/integration-model.json"));

        new CommandLine(new ScannerCommand(new IEndpointScannerService[] {scannerService}, exportModelServices)).execute("integration");

        File currentJsonFile = new File("model.json");

        assertThat(currentJsonFile).exists().isFile().canRead();
        JSONAssert.assertEquals(expectedJson.replace("###version###", modelVersion), contentOf(currentJsonFile), JSONCompareMode.STRICT);
    }

    @Test
    void shouldMatchCurrentModel_whenWriteDot() {
        String expectedDot=contentOf(new File("src/test/resources/model/integration-model.dot"));

        new CommandLine(new ScannerCommand(new IEndpointScannerService[] {scannerService}, exportModelServices)).execute("integration");

        File currentDotFile = new File("model.dot");

        assertThat(currentDotFile).exists().isFile().canRead();
        assertThat(contentOf(currentDotFile)).isEqualTo(expectedDot.replace("\r\n", "\n"));
    }
}