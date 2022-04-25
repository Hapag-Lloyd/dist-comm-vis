package com.hlag.tools.commvis.domain.command;

import com.hlag.tools.commvis.domain.adapter.ObjectMapperConfiguration;
import com.hlag.tools.commvis.domain.command.ScannerCommand;
import com.hlag.tools.commvis.domain.port.DistributedCommunicationVisualizerApplication;
import com.hlag.tools.commvis.service.ExportModelService;
import com.hlag.tools.commvis.service.IEndpointScannerService;
import com.hlag.tools.commvis.service.JaxRsEndpointScannerImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import picocli.CommandLine;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

@SpringBootTest(classes = {JaxRsEndpointScannerImpl.class, ScannerCommand.class, ExportModelService.class, ObjectMapperConfiguration.class})
class ScannerCommandIT {
    @Autowired
    private IEndpointScannerService scannerService;

    @Autowired
    private ExportModelService exportModelService;

    @Value("${git.tags}")
    private String modelVersion;

    @Test
    void shouldMatchCurrentModelJson_whenWriteJson() throws Exception {
        new CommandLine(new ScannerCommand(new IEndpointScannerService[] {scannerService}, exportModelService)).execute("integration");

        File currentModelFile = new File("model.json");

        assertThat(currentModelFile).exists().isFile().canRead();
        assertThat(contentOf(currentModelFile)).isEqualTo(String.format("{\"version\":\"%s\",\"endpoints\":[{\"classname\":\"integration.Endpoints\",\"method_name\":\"receivesAPostRequest\",\"type\":\"POST\"}]}", modelVersion));
    }
}