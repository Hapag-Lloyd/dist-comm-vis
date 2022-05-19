package com.hlag.tools.commvis.adapter.out;

import com.hlag.tools.commvis.application.port.out.FileWriterPort;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
@Component
public class FileWriterAdapter implements FileWriterPort {
    @Override
    public void writeToFile(String filename, String content) {
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write(content);
        } catch (IOException e) {
            log.error("Failed to write file.", e);
            ExceptionUtils.rethrow(e);
        }
    }
}
