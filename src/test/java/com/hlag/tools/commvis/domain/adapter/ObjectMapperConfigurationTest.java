package com.hlag.tools.commvis.domain.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.io.StringWriter;

class ObjectMapperConfigurationTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        objectMapper = new ObjectMapperConfiguration().getObjectMapper();
    }

    @Test
    void shouldStoreNamesInSnakeCase_whenSerialize() throws IOException, JSONException {
        TestJson givenObject = new TestJson("value", null);
        JSONObject expectedJson = new JSONObject();
        expectedJson.put("nice_name", "value");

        StringWriter sw = new StringWriter();

        objectMapper.writerFor(TestJson.class).writeValue(sw, givenObject);

        String actualJson = sw.toString();
        JSONAssert.assertEquals(expectedJson.toString(), actualJson, JSONCompareMode.LENIENT);
    }

    @Test
    void shouldRemoveNullObjects_whenSerialize() throws IOException, JSONException {
        TestJson givenObject = new TestJson("value", null);
        JSONObject expectedJson = new JSONObject();
        expectedJson.put("nice_name", "value");

        StringWriter sw = new StringWriter();

        objectMapper.writerFor(TestJson.class).writeValue(sw, givenObject);

        String actualJson = sw.toString();
        JSONAssert.assertEquals(expectedJson.toString(), actualJson, JSONCompareMode.STRICT);
    }

    @AllArgsConstructor
    static class TestJson {
        // use private here to check the Jackson field visibility
        private String niceName;
        private String alwaysNull;
    }
}