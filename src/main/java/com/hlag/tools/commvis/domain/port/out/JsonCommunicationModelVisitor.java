package com.hlag.tools.commvis.domain.port.out;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hlag.tools.commvis.domain.model.AbstractCommunicationModelVisitor;
import com.hlag.tools.commvis.domain.model.CommunicationModel;
import com.hlag.tools.commvis.domain.model.HttpEndpoint;
import com.hlag.tools.commvis.domain.model.IEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;

/**
 * Transforms the internal model into a JSON representation.
 */
@Slf4j
@Component
public class JsonCommunicationModelVisitor extends AbstractCommunicationModelVisitor {
    private String version;
    private StringBuilder httpEndpointsJson = new StringBuilder();

    public JsonCommunicationModelVisitor(@Value("${git.tags}") String gitTag) {
        this.version = gitTag;
    }

    public String getJson() {
        return String.format("{\"version\": \"%s\", \"http_endpoints\": [", version) + httpEndpointsJson.toString() + "]}";
    }

    @Override
    public void visit(HttpEndpoint httpEndpoint) {
        JsonFactory factory = new JsonFactory();
        StringWriter jsonObjectWriter = new StringWriter();

        try (JsonGenerator generator = factory.createGenerator(jsonObjectWriter)) {
            generator.useDefaultPrettyPrinter();

            generator.writeStartObject();
            generator.writeStringField("class_name", httpEndpoint.getClassName());
            generator.writeStringField("method_name", httpEndpoint.getMethodName());
            generator.writeStringField("type", httpEndpoint.getType());
            generator.writeEndObject();
        } catch (IOException e) {
            log.error("Failed to convert HttpEndpoint to JSON.", e);
            ExceptionUtils.rethrow(e);
        }

        this.httpEndpointsJson.append(jsonObjectWriter.toString());
    }
}
