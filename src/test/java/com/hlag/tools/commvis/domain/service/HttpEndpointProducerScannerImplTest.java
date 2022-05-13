package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.model.HttpProducer;
import com.hlag.tools.commvis.analyzer.model.ISenderReceiverCommunication;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class HttpEndpointProducerScannerImplTest {
    private HttpEndpointProducerScannerImpl clazz;

    @BeforeEach
    public void init() {
        clazz = new HttpEndpointProducerScannerImpl();
    }

    @Test
    void shouldFindAllHttpProducers_whenScanClasspath() {
        Collection<ISenderReceiverCommunication> actualEndpoints = clazz.scanSenderAndReceiver("test.http");

        assertThat(actualEndpoints).extracting(Object::getClass).filteredOn(HttpProducer.class::isAssignableFrom).hasSize(1);
    }

    @Test
    void shouldExtractAllHttpProducerInformation_whenScanClasspath() {
        Collection<HttpProducer> actualEndpoints = (Collection) clazz.scanSenderAndReceiver("test.http");

        assertThat(actualEndpoints).extracting(HttpProducer::getClassName, HttpProducer::getMethodName, HttpProducer::getType, HttpProducer::getPath, HttpProducer::getDestinationProjectId).contains(new Tuple("test.http.CallHttpsEndpoints", "getCustomerInvoices", "GET", "customers/{customerId}/invoices", "4711"));
    }
}