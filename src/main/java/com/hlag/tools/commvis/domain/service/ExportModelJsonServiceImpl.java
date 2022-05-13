package com.hlag.tools.commvis.domain.service;

import com.google.gson.GsonBuilder;
import com.hlag.tools.commvis.analyzer.model.CommunicationModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Convert the internal model into a JSON representation and save it to a file.
 */
@Service
@Slf4j
public class ExportModelJsonServiceImpl implements IExportModelService {
    public void export(CommunicationModel model, String filename) {
        String jsonContent = new GsonBuilder().setPrettyPrinting().create().toJson(model);

        try (FileWriter fw = new FileWriter(filename + ".json")) {
            fw.write(jsonContent);
        } catch (IOException e) {
            log.error("Failed to write model file.", e);
            ExceptionUtils.rethrow(e);
        }
    }

}
