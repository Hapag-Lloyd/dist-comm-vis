package com.hlag.tools.commvis.application.port.out;

import com.hlag.tools.commvis.analyzer.model.CommunicationModel;
import com.hlag.tools.commvis.analyzer.model.EndpointFactory;
import com.hlag.tools.commvis.analyzer.model.HttpConsumer;
import com.hlag.tools.commvis.analyzer.model.ISenderReceiverCommunication;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;

class JsonCommunicationModelVisitorTest {
    private JsonCommunicationModelVisitor jsonCommunicationModelVisitor;

    @BeforeEach
    void init() {
        this.jsonCommunicationModelVisitor = new JsonCommunicationModelVisitor("my-git-tag");
    }

    @Test
    void shouldCreateValidJson_whenGetJson_givenMultipleHttpEndpoints() {
        CommunicationModel givenModel = new CommunicationModel("4711", "my-project", "1.2.3");

        givenModel.addSenderReceiver(EndpointFactory.get().createHttpConsumer("classname", "methodname", "type", "path"));
        givenModel.addSenderReceiver(EndpointFactory.get().createHttpConsumer("classname1", "methodname1", "type1", "path1"));

        Assertions.assertThatNoException().isThrownBy(() -> {
            givenModel.visit(jsonCommunicationModelVisitor);
            String actualJson = jsonCommunicationModelVisitor.getJson();

            new JSONObject(actualJson);
        });
    }
}