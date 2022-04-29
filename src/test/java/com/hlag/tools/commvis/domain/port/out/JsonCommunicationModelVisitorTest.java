package com.hlag.tools.commvis.domain.port.out;

import com.hlag.tools.commvis.domain.model.CommunicationModel;
import com.hlag.tools.commvis.domain.model.HttpEndpoint;
import com.hlag.tools.commvis.domain.model.IEndpoint;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
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
        Collection<IEndpoint> givenHttpEndpoints = new HashSet<>();
        givenHttpEndpoints.add(new HttpEndpoint("classname", "methodname", "type", "path"));
        givenHttpEndpoints.add(new HttpEndpoint("classname1", "methodname1", "type1", "path1"));

        CommunicationModel givenModel = new CommunicationModel("4711", "my-project", givenHttpEndpoints);

        Assertions.assertThatNoException().isThrownBy(() -> {
            givenModel.visit(jsonCommunicationModelVisitor);
            String actualJson = jsonCommunicationModelVisitor.getJson();

            new JSONObject(actualJson);
        });
    }
}