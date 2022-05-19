package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.model.EndpointFactory;
import com.hlag.tools.commvis.analyzer.model.HttpProducer;
import com.hlag.tools.commvis.analyzer.model.ISenderReceiverCommunication;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.xml.stream.events.EndDocument;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class HttpEndpointProducerScannerImplTest {
    @Mock
    private EndpointFactory endpointFactory;

    @InjectMocks
    private HttpEndpointProducerScannerImpl clazz;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindAllHttpProducers_whenScanClasspath() {
        Collection<ISenderReceiverCommunication> actualEndpoints = clazz.scanSenderAndReceiver("test.http");

        Mockito.verify(endpointFactory, Mockito.times(1)).createHttpProducer(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void shouldExtractAllHttpProducerInformation_whenScanClasspath() {
        Collection<HttpProducer> actualEndpoints = (Collection) clazz.scanSenderAndReceiver("test.http");

        Mockito.verify(endpointFactory, Mockito.times(1)).createHttpProducer(Mockito.eq("test.http.CallHttpsEndpoints"), Mockito.eq("getCustomerInvoices"), Mockito.eq("GET"), Mockito.eq("customers/{customerId}/invoices"), Mockito.eq("4711"));
    }
}