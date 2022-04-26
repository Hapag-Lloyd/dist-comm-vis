package com.hlag.tools.commvis.service;

import com.hlag.tools.commvis.domain.model.IEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

@Service
@Slf4j
public class ExportModelDotServiceImpl implements IExportModelService {
    public void export(Collection<IEndpoint> endpoints, String filename) {
        StringBuilder sb = new StringBuilder();

        sb.append("digraph G {\n");

        int nodeNumber = 0;
        for (IEndpoint endpoint : endpoints) {
            String label = endpoint.getClassName() + "." + endpoint.getMethodName() + "\\n" + endpoint.getType();
            sb.append(String.format("  \"%d\" [label=\"%s\"]\n", nodeNumber, label));
            ++nodeNumber;
        }

        sb.append("\n");

        for (int i = 0; i < endpoints.size(); i++) {
            sb.append(String.format("  \"%d\" -> \"application\"\n", i));
        }

        sb.append("}");

        try (FileWriter fw = new FileWriter((filename + ".dot"))) {
            fw.write(sb.toString());
        } catch (IOException e) {
            log.error("Failed to write dot file.", e);
            ExceptionUtils.rethrow(e);
        }
    }
}
