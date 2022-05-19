package com.hlag.tools.commvis.adapter.out;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

class FileWriterAdapterTest {
    @Test
    void shouldWriteContentToFile_whenWriteToFile() {
        String givenFilename = "test-file.txt";
        String givenContent = "some content";

        new FileWriterAdapter().writeToFile(givenFilename, givenContent);

        File actualFile = new File(givenFilename);

        assertThat(actualFile).exists().isFile().canRead();
        assertThat(contentOf(actualFile)).isEqualTo(givenContent);
    }
}