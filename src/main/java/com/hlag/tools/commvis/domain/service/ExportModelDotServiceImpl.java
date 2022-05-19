package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.model.CommunicationModel;
import com.hlag.tools.commvis.application.port.out.DotCommunicationModelVisitor;
import com.hlag.tools.commvis.application.port.out.FileWriterPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Convert the internal model into a DOT (GraphViz) representation and save it to a file.
 */
@Service
@RequiredArgsConstructor
public class ExportModelDotServiceImpl implements IExportModelService {
    private final DotCommunicationModelVisitor visitor;
    private final FileWriterPort fileWriter;

    public void export(CommunicationModel model, String filename) {
        model.visit(visitor);

        String dotContent = visitor.getDotContent();
        fileWriter.writeToFile(filename + ".dot", dotContent);
    }
}
