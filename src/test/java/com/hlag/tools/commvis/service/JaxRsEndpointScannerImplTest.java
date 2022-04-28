package com.hlag.tools.commvis.service;

import com.hlag.tools.commvis.domain.model.HttpEndpoint;
import com.hlag.tools.commvis.domain.model.IEndpoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.HttpMethod;
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
        Set<IEndpoint> actualEndpoints = clazz.scanClasspath("test.jaxrs");

        assertThat(actualEndpoints).extracting(IEndpoint::getType).containsExactlyInAnyOrder(HttpMethod.GET, HttpMethod.HEAD, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.OPTIONS, HttpMethod.PATCH);
    }

    @Test
    void shouldExtractAllEndpointInformation_whenScanClasspath() {
        IEndpoint expectedEndpoint = new HttpEndpoint("test.jaxrs.Endpoints", "receivesAPostRequest", "POST", "endpoint/a");

        Set<IEndpoint> actualEndpoints = clazz.scanClasspath("test.jaxrs");

        assertThat(actualEndpoints).contains(expectedEndpoint);
    }
}