package com.hlag.tools.commvis.service;

import com.hlag.tools.commvis.domain.model.HttpEndpoint;
import com.hlag.tools.commvis.domain.model.IEndpoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.HttpMethod;
import java.util.Collection;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class JaxRsEndpointScannerImplTest {
    private JaxRsEndpointScannerImpl clazz;

    @BeforeEach
    public void init() {
        clazz = new JaxRsEndpointScannerImpl();
    }

    @Test
    void shouldFindEndpointsForAllHttpMethods_whenScanClasspath() {
        Collection<HttpEndpoint> actualEndpoints = (Collection) clazz.scanClasspath("test.jaxrs");

        assertThat(actualEndpoints).extracting(HttpEndpoint::getType).containsExactlyInAnyOrder(HttpMethod.GET, HttpMethod.HEAD, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.OPTIONS, HttpMethod.PATCH);
    }

    @Test
    void shouldExtractAllEndpointInformation_whenScanClasspath() {
        HttpEndpoint expectedEndpoint = new HttpEndpoint("test.jaxrs.Endpoints", "receivesAPostRequest", "POST", "endpoint/a");

        Collection<HttpEndpoint> actualEndpoints = (Collection) clazz.scanClasspath("test.jaxrs");

        assertThat(actualEndpoints).contains(expectedEndpoint);
    }
}