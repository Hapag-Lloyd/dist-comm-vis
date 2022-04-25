package com.hlag.tools.commvis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.hlag.tools.commvis.domain.model.HttpEndpoint;
import com.hlag.tools.commvis.domain.model.IEndpoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class ExportModelServiceTest {
    @Spy
    private ObjectMapper objectMapper;
    private ExportModelService exportModelService;
    @Mock
    private ObjectWriter mockedWriter;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        this.exportModelService = new ExportModelService(objectMapper, "1.2.3");
        when(objectMapper.writerFor(any(Class.class))).thenReturn(mockedWriter);
    }

    @Test
    public void shouldWriteModelToFile_whenWriteJson_givenFilename() throws IOException {
        String givenFilename = "my-file.json";

        exportModelService.writeJson(Collections.emptySet(), givenFilename);

        Mockito.verify(mockedWriter, Mockito.times(1)).writeValue(eq(new File(givenFilename)), any());
    }
}