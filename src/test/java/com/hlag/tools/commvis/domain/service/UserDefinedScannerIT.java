package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.scanner.user.UserDefinedScanner;
import com.hlag.tools.commvis.analyzer.service.IScannerService;
import com.hlag.tools.commvis.PropertyFilesConfiguration;
import com.hlag.tools.commvis.application.port.in.ScannerCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserDefinedScannerIT {
    @Autowired
    private IScannerService[] scannerServices;

    @Test
    void shouldFindUserDefinedScannersAndStandardScannersInPackage()  {
        assertThat(scannerServices).hasSize(9);
    }
}
