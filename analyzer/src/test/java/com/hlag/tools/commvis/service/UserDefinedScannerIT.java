package com.hlag.tools.commvis.service;

import com.hlag.tools.commvis.analyzer.scanner.user.UserDefinedScanner;
import com.hlag.tools.commvis.analyzer.service.IScannerService;
import com.hlag.tools.commvis.domain.adapter.PropertyFilesConfiguration;
import com.hlag.tools.commvis.domain.command.ScannerCommand;
import com.hlag.tools.commvis.domain.port.out.DotCommunicationModelVisitor;
import com.hlag.tools.commvis.domain.port.out.JsonCommunicationModelVisitor;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import picocli.CommandLine;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

@SpringBootTest(classes = {ScannerCommand.class, PropertyFilesConfiguration.class, UserDefinedScanner.class})
public class UserDefinedScannerIT {
    @Autowired
    private IScannerService[] scannerServices;

    @Test
    //TODO this test is strange as we add the service above and check it it's there.
    void shouldFindUSerDefinedScannersInPackage()  {
        assertThat(scannerServices).hasSize(1).extracting(Object::getClass).contains(UserDefinedScanner.class);
    }
}
