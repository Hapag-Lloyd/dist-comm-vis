package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.model.CommunicationModel;
import com.hlag.tools.commvis.application.port.out.FileWriterPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ExportModelJsonServiceImplTest {
    @Mock
    private FileWriterPort fileWriter;

    @InjectMocks
    private ExportModelJsonServiceImpl exportModelJsonService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldWriteJsonFile_whenExport() {
        String givenFilename = "file";
        CommunicationModel givenModel = new CommunicationModel("id", "name", "version");

        exportModelJsonService.export(givenModel, givenFilename);

        Mockito.verify(fileWriter, Mockito.times(1)).writeToFile(Mockito.eq(givenFilename + ".json"), Mockito.any());
    }
}