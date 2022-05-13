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

        Assertions.assertThat(actualModelFiles).hasSize(6);
    }
}