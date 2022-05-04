package com.hlag.tools.commvis.application.port.in;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
public class ScannerCommand {
    String rootPackage;
    String projectId;
    String projectName;
}
