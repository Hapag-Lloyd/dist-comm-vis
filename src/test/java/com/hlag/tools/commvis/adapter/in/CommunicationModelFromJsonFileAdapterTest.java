package com.hlag.tools.commvis.adapter.in;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Collection;

class CommunicationModelFromJsonFileAdapterTest {
    private CommunicationModelFromJsonFileAdapter adapter;

    @BeforeEach
    void init() {
        adapter = new CommunicationModelFromJsonFileAdapter();
    }

    @Test
    void shouldFindAllModelFilesInSubDirectories_when() {
        String givenBaseDirectory = "src/test/resources/json-file-adapter/";

        Collection<Path> actualModelFiles = adapter.getModelFiles(givenBaseDirectory);

        Assertions.assertThat(actualModelFiles).hasSize(6).extracting(Path::toString).containsExactlyInAnyOrder(
                "src\\test\\resources\\json-file-adapter\\model-newer-name.json",
                "src\\test\\resources\\json-file-adapter\\model.json",
                "src\\test\\resources\\json-file-adapter\\subdir1\\model-old-name.json",
                "src\\test\\resources\\json-file-adapter\\subdir1\\model.json",
                "src\\test\\resources\\json-file-adapter\\subdir2\\model-new-name.json",
                "src\\test\\resources\\json-file-adapter\\subdir2\\model.json");
    }
}