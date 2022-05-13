package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.model.HttpConsumer;
import com.hlag.tools.commvis.analyzer.model.ISenderReceiverCommunication;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.HttpMethod;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class JaxRsHttpEndpointConsumerScannerImplTest {
    private JaxRsHttpEndpointConsumerScannerImpl clazz;

    @BeforeEach
    public void init() {
        clazz = new JaxRsHttpEndpointConsumerScannerImpl();
    }

    @Test
    void shouldFindEndpointsForAllHttpMethods_whenScanClasspath() {
        Collection<ISenderReceiverCommunication> actualEndpoints = clazz.scanSenderAndReceiver("test.http.jaxrs");

        assertThat(actualEndpoints).filteredOn(e -> HttpConsumer.class.isAssignableFrom(e.getClass())).extracting(c -> ((HttpConsumer) c).getType()).containsExactlyInAnyOrder(HttpMethod.GET, HttpMethod.HEAD, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.OPTIONS, HttpMethod.PATCH);
    }

    @Test
    void shouldExtractAllEndpointInformation_whenScanClasspath() {
        Collection<HttpConsumer> actualEndpoints = (Collection) clazz.scanSenderAndReceiver("test.http.jaxrs");

        assertThat(actualEndpoints).extracting(HttpConsumer::getClassName, HttpConsumer::getMethodName, HttpConsumer::getType, HttpConsumer::getPath).contains(new Tuple("test.http.jaxrs.ConsumerEndpoints", "receivesAPostRequest", "POST", "endpoint/a"));
    }
}