package com.hlag.tools.commvis.service;

import com.hlag.tools.commvis.analyzer.model.HttpReceiver;
import com.hlag.tools.commvis.domain.service.JaxRsEndpointScannerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.HttpMethod;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class JaxRsEndpointScannerImplTest {
    private JaxRsEndpointScannerImpl clazz;

    @BeforeEach
    public void init() {
        clazz = new JaxRsEndpointScannerImpl();
    }

    @Test
    void shouldFindEndpointsForAllHttpMethods_whenScanClasspath() {
        Collection<HttpReceiver> actualEndpoints = (Collection) clazz.scanSenderAndReceiver("test.jaxrs");

        assertThat(actualEndpoints).extracting(HttpReceiver::getType).containsExactlyInAnyOrder(HttpMethod.GET, HttpMethod.HEAD, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.OPTIONS, HttpMethod.PATCH);
    }

    @Test
    void shouldExtractAllEndpointInformation_whenScanClasspath() {
        HttpReceiver expectedEndpoint = new HttpReceiver("test.jaxrs.Endpoints", "receivesAPostRequest", "POST", "endpoint/a");

        Collection<HttpReceiver> actualEndpoints = (Collection) clazz.scanSenderAndReceiver("test.jaxrs");

        assertThat(actualEndpoints).contains(expectedEndpoint);
    }
}