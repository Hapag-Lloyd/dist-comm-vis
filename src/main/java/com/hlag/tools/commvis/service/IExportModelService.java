package com.hlag.tools.commvis.service;

import com.hlag.tools.commvis.domain.model.CommunicationModel;

/**
 * Exports the internal model.
 */
public interface IExportModelService {
    /**
     * Converts the internal model and stores it as file.
     * @param model the model to export
     * @param filename the output file name without an extension
     */
    void export(CommunicationModel model, String filename);
}
