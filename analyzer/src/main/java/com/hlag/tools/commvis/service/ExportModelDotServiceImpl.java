package com.hlag.tools.commvis.service;

import com.hlag.tools.commvis.domain.model.CommunicationModel;
import com.hlag.tools.commvis.domain.port.out.DotCommunicationModelVisitor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Convert the internal model into a DOT (GraphViz) representation and save it to a file.
 */
@Service
@Slf4j
public class ExportModelDotServiceImpl implements IExportModelService {
    private final DotCommunicationModelVisitor visitor;

    public ExportModelDotServiceImpl(DotCommunicationModelVisitor visitor) {
        this.visitor = visitor;
    }

    public void export(CommunicationModel model, String filename) {
        model.visit(visitor);
        String dotContent = visitor.getDotContent();

        try (FileWriter fw = new FileWriter(new File(filename + ".dot"))) {
            fw.write(dotContent);
        } catch (IOException e) {
            log.error("Failed to write dot model file.", e);
            ExceptionUtils.rethrow(e);
        }
    }
}
