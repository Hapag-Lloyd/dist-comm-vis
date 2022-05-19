package com.hlag.tools.commvis.adapter.in;

import com.hlag.tools.commvis.analyzer.model.CommunicationModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

class CommunicationModelFromJsonFileAdapterTest {
    private CommunicationModelFromJsonFileAdapter adapter;

    @BeforeEach
    void init() {
        adapter = new CommunicationModelFromJsonFileAdapter();
    }

    @Test
    void shouldFindAllModelFilesInSubDirectories_when() {
        //Path p = Paths.get("src/test/resources/json-file-adapter/");
        Path p = Paths.get("/home/runner/work/dist-comm-vis/dist-comm-vis/src/test/resources/json-file-adapter");
        String givenBaseDirectory = p.toFile().getAbsolutePath();

        Collection<Path> actualModelFiles = adapter.getModelFiles(givenBaseDirectory);

        Assertions.assertThat(actualModelFiles).hasSize(6);
    }

    @Test
    void shouldReadModelFromFile_whenGetModelFromFile() {
        CommunicationModel actualModel = adapter.getModelFromFile(Paths.get("src/test/resources/json-file-adapter/model.json"));

        Assertions.assertThat(actualModel).isNotNull();
    }
}