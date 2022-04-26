package com.hlag.tools.commvis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hlag.tools.commvis.domain.model.IEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

@Service
@Slf4j
public class ExportModelJsonServiceImpl implements IExportModelService {

    private final ObjectMapper objectMapper;
    private final ExportModel model = new ExportModel();

    public ExportModelJsonServiceImpl(ObjectMapper objectMapper, @Value("${git.tags}") String gitTag) {
        this.objectMapper = objectMapper;
        this.model.version = gitTag;
    }

    public void export(Collection<IEndpoint> endpoints, String filename) {
        model.endpoints = endpoints;

        try {
            objectMapper.writerFor(ExportModel.class).writeValue(new File(filename + ".json"), model);
        } catch (IOException e) {
            log.error("Failed to write model file.", e);
            ExceptionUtils.rethrow(e);
        }
    }

    static class ExportModel {
        String version;
        Collection<IEndpoint> endpoints;
    }
}
