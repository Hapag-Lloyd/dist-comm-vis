package com.hlag.tools.commvis.application.port.out;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.hlag.tools.commvis.analyzer.model.*;
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
public class JsonCommunicationModelVisitor extends AbstractCommunicationModelVisitor {
    private final String version;
    private final StringBuilder httpConsumersJson = new StringBuilder();
    private final StringBuilder httpProducersJson = new StringBuilder();
    private final StringBuilder jmsEndpointsJson = new StringBuilder();
    private final StringBuilder modelSettings = new StringBuilder();

    public JsonCommunicationModelVisitor(String gitTag) {
        this.version = gitTag;
    }

    public String getJson() {
        return String.format("{%s, \"http_consumers\": [%s], \"http_producers\": [%s], \"jms_endpoints\": [%s]}", modelSettings, httpConsumersJson, httpProducersJson, jmsEndpointsJson);
    }

    @Override
    public void visit(CommunicationModel model) {
        modelSettings.append(String.format("\"version\": \"%s\", \"id\": \"%s\", \"name\": \"%s\"", version, model.getProjectId(), model.getProjectName()));
    }

    @Override
    public void visit(HttpConsumer endpoint) {
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

        if (httpConsumersJson.length() > 0) {
            httpConsumersJson.append(',');
        }

        httpConsumersJson.append(jsonObjectWriter);
    }

    @Override
    public void visit(HttpProducer producer) {
        JsonFactory factory = new JsonFactory();
        StringWriter jsonObjectWriter = new StringWriter();

        try (JsonGenerator generator = factory.createGenerator(jsonObjectWriter)) {
            generator.useDefaultPrettyPrinter();

            generator.writeStartObject();
            generator.writeStringField("class_name", producer.getClassName());
            generator.writeStringField("method_name", producer.getMethodName());
            generator.writeStringField("type", producer.getType());
            generator.writeStringField("path", producer.getPath());
            generator.writeStringField("destination_project_id", producer.getDestinationProjectId());
            generator.writeEndObject();
        } catch (IOException e) {
            log.error("Failed to convert http(s) producer to JSON.", e);
            ExceptionUtils.rethrow(e);
        }

        if (httpProducersJson.length() > 0) {
            httpProducersJson.append(',');
        }

        httpProducersJson.append(jsonObjectWriter);
    }

    @Override
    public void visit(JmsReceiver endpoint) {
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
