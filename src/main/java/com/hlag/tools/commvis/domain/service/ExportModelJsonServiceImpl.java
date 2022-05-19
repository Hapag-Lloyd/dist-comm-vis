package com.hlag.tools.commvis.domain.service;

import com.google.gson.GsonBuilder;
import com.hlag.tools.commvis.analyzer.model.CommunicationModel;
import com.hlag.tools.commvis.application.port.out.FileWriterPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Convert the internal model into a JSON representation and save it to a file.
 */
@Service
@RequiredArgsConstructor
public class ExportModelJsonServiceImpl implements IExportModelService {
    private final FileWriterPort fileWriter;

    public void export(CommunicationModel model, String filename) {
        String jsonContent = new GsonBuilder().setPrettyPrinting().create().toJson(model);
        fileWriter.writeToFile(filename + ".json", jsonContent);
    }
}
