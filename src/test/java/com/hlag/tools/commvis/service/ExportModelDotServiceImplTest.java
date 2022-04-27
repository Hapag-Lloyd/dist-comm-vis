package com.hlag.tools.commvis.service;

import com.hlag.tools.commvis.domain.model.CommunicationModel;
import com.hlag.tools.commvis.domain.port.out.JsonCommunicationModelVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class ExportModelDotServiceImplTest {
    @Mock
    private JsonCommunicationModelVisitor visitor;

    private ExportModelJsonServiceImpl exportModelJsonServiceImpl;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        this.exportModelJsonServiceImpl = new ExportModelJsonServiceImpl(visitor);
    }

    @Test
    public void shouldWriteModelToFile_whenWriteJson_givenFilename() throws IOException {
        String givenFilename = "my-file";
        String expectedFilename = givenFilename + ".json";

        exportModelJsonServiceImpl.export(new CommunicationModel(Collections.emptySet()), givenFilename);
    }
}