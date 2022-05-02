package com.hlag.tools.commvis.application.port.in;

import com.hlag.tools.commvis.analyzer.model.CommunicationModel;
import com.hlag.tools.commvis.analyzer.model.ISenderReceiverCommunication;
import com.hlag.tools.commvis.analyzer.service.IScannerService;
import com.hlag.tools.commvis.domain.service.IExportModelService;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.Callable;

@Slf4j
@Value
public class ScannerCommand {
    private final String rootPackage;
    private final String projectId;
    private final String projectName;
}
