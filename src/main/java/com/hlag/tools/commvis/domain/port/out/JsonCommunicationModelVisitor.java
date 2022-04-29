package com.hlag.tools.commvis.domain.port.out;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.hlag.tools.commvis.domain.model.AbstractCommunicationModelVisitor;
import com.hlag.tools.commvis.domain.model.CommunicationModel;
import com.hlag.tools.commvis.domain.model.HttpEndpoint;
import com.hlag.tools.commvis.domain.model.JmsEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Transforms the internal model into a JSON representation.
 */
@Slf4j
@Component
public class JsonCommunicationModelVisitor extends AbstractCommunicationModelVisitor {
    private String version;
    private StringBuilder httpEndpointsJson = new StringBuilder();
    private StringBuilder jmsEndpointsJson = new StringBuilder();
    private StringBuilder modelSettings = new StringBuilder();

    public JsonCommunicationModelVisitor(@Value("${git.tags}") String gitTag) {
        this.version = gitTag;
    }

    public String getJson() {
        return String.format("{%s, \"http_endpoints\": [%s], \"jms_endpoints\": [%s]}", modelSettings, httpEndpointsJson, jmsEndpointsJson);
    }

    @Override
    public void visit(CommunicationModel model) {
        modelSettings.append(String.format("\"version\": \"%s\", \"id\": \"%s\", \"name\": \"%s\"", version, model.getProjectId(), model.getProjectName()));
    }

    @Override
    public void visit(HttpEndpoint endpoint) {
        JsonFactory factory = new JsonFactory();
        StringWriter jsonObjectWriter = new StringWriter();

        try (JsonGenerator generator = factory.createGenerator(jsonObjectWriter)) {
            generator.useDefaultPrettyPrinter();

            generator.writeStartObject();
            generator.writeStringField("class_name", endpoint.getClassName());
            generator.writeStringField("method_name", endpoint.getMethodName());
            generator.writeStringField("type", endpoint.getType());
            generator.writeStringField("path", endpoint.getPath());
            generator.writeEndObject();
        } catch (IOException e) {
            log.error("Failed to convert HttpEndpoint to JSON.", e);
            ExceptionUtils.rethrow(e);
        }

        if (httpEndpointsJson.length() > 0) {
            httpEndpointsJson.append(',');
        }

        httpEndpointsJson.append(jsonObjectWriter);
    }

    @Override
    public void visit(JmsEndpoint endpoint) {
        JsonFactory factory = new JsonFactory();
        StringWriter jsonObjectWriter = new StringWriter();

        try (JsonGenerator generator = factory.createGenerator(jsonObjectWriter)) {
            generator.useDefaultPrettyPrinter();

            generator.writeStartObject();
            generator.writeStringField("class_name", endpoint.getClassName());
            generator.writeStringField("destination_type", endpoint.getDestinationType());
            generator.writeStringField("destination", endpoint.getDestination());
            generator.writeEndObject();
        } catch (IOException e) {
            log.error("Failed to convert JmsEndpoint to JSON.", e);
            ExceptionUtils.rethrow(e);
        }

        if (jmsEndpointsJson.length() > 0) {
            jmsEndpointsJson.append(',');
        }

        jmsEndpointsJson.append(jsonObjectWriter);
    }
}
