package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.model.CommunicationModel;
import com.hlag.tools.commvis.application.port.out.JsonCommunicationModelVisitor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Convert the internal model into a JSON representation and save it to a file.
 */
@Service
@Slf4j
public class ExportModelJsonServiceImpl implements IExportModelService {

    private final JsonCommunicationModelVisitor visitor;

    public ExportModelJsonServiceImpl(@Value("${git.tags}") String version) {
        this.visitor = new JsonCommunicationModelVisitor(version);
    }

    public void export(CommunicationModel model, String filename) {
        model.visit(visitor);

        String jsonContent = visitor.getJson();

        try (FileWriter fw = new FileWriter(new File(filename + ".json"))) {
            fw.write(jsonContent);
        } catch (IOException e) {
            log.error("Failed to write model file.", e);
            ExceptionUtils.rethrow(e);
        }
    }

}
