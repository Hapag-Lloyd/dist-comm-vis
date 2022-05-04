package com.hlag.tools.commvis.service;

import com.hlag.tools.commvis.analyzer.model.HttpConsumer;
import com.hlag.tools.commvis.analyzer.model.HttpProducer;
import com.hlag.tools.commvis.analyzer.model.ISenderReceiverCommunication;
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
        Collection<ISenderReceiverCommunication> actualEndpoints = clazz.scanSenderAndReceiver("test.jaxrs");

        assertThat(actualEndpoints).filteredOn(e -> HttpConsumer.class.isAssignableFrom(e.getClass())).extracting(c -> ((HttpConsumer) c).getType()).containsExactlyInAnyOrder(HttpMethod.GET, HttpMethod.HEAD, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.OPTIONS, HttpMethod.PATCH);
    }

    @Test
    void shouldExtractAllEndpointInformation_whenScanClasspath() {
        HttpConsumer expectedEndpoint = new HttpConsumer("test.jaxrs.Endpoints", "receivesAPostRequest", "POST", "endpoint/a");

        Collection<HttpConsumer> actualEndpoints = (Collection) clazz.scanSenderAndReceiver("test.jaxrs");

        assertThat(actualEndpoints).contains(expectedEndpoint);
    }

    @Test
    void shouldFindAllHttpProducers_whenScanClasspath() {
        Collection<ISenderReceiverCommunication> actualEndpoints = clazz.scanSenderAndReceiver("test.jaxrs");

        assertThat(actualEndpoints).extracting(Object::getClass).filteredOn(c -> HttpProducer.class.isAssignableFrom(c)).hasSize(1);
    }

    @Test
    void shouldExtractAllHttpProducerInformation_whenScanClasspath() {
        HttpProducer expectedProducer = new HttpProducer("test.jaxrs.CallHttpsEndpoints", "getCustomerInvoices", "GET", "customers/{customerId}/invoices", "4711");

        Collection<ISenderReceiverCommunication> actualEndpoints = clazz.scanSenderAndReceiver("test.jaxrs");

        assertThat(actualEndpoints).contains(expectedProducer);
    }
}