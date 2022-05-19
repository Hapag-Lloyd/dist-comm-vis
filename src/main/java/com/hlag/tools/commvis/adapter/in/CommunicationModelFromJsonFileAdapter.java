package com.hlag.tools.commvis.adapter.in;

import com.google.gson.Gson;
import com.hlag.tools.commvis.analyzer.model.CommunicationModel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class CommunicationModelFromJsonFileAdapter {
    @SneakyThrows(IOException.class)
    public Collection<Path> getModelFiles(String baseDir) {
        try (Stream<Path> files = Files.walk(Paths.get(baseDir))) {
            return files
                    .filter(f -> f.getFileName().toString().startsWith("model"))
                    .filter(f -> f.getFileName().toString().endsWith(".json"))
                    .collect(Collectors.toList());
        }
    }

    @SneakyThrows(IOException.class)
    public CommunicationModel getModelFromFile(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return new Gson().fromJson(reader, CommunicationModel.class);
        }
    }
}
