package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.model.EndpointFactory;
import com.hlag.tools.commvis.analyzer.model.HttpConsumer;
import com.hlag.tools.commvis.analyzer.model.ISenderReceiverCommunication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.HttpMethod;
import java.util.Collection;

class SpringHttpEndpointScannerImplTest {
    @Mock
    private EndpointFactory endpointFactory;
    @InjectMocks
    private SpringHttpEndpointScannerImpl clazz;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindEndpointsForAllHttpMethods_whenScanClasspath() {
        Collection<ISenderReceiverCommunication> actualEndpoints = clazz.scanSenderAndReceiver("test.http.spring");

        Mockito.verify(endpointFactory, Mockito.times(1)).createHttpConsumer(Mockito.any(), Mockito.any(), Mockito.eq(HttpMethod.GET), Mockito.any());
        Mockito.verify(endpointFactory, Mockito.times(1)).createHttpConsumer(Mockito.any(), Mockito.any(), Mockito.eq(HttpMethod.HEAD), Mockito.any());
        Mockito.verify(endpointFactory, Mockito.times(1)).createHttpConsumer(Mockito.any(), Mockito.any(), Mockito.eq(HttpMethod.POST), Mockito.any());
        Mockito.verify(endpointFactory, Mockito.times(1)).createHttpConsumer(Mockito.any(), Mockito.any(), Mockito.eq(HttpMethod.DELETE), Mockito.any());
        Mockito.verify(endpointFactory, Mockito.times(1)).createHttpConsumer(Mockito.any(), Mockito.any(), Mockito.eq(HttpMethod.PUT), Mockito.any());
        Mockito.verify(endpointFactory, Mockito.times(1)).createHttpConsumer(Mockito.any(), Mockito.any(), Mockito.eq(HttpMethod.OPTIONS), Mockito.any());
        Mockito.verify(endpointFactory, Mockito.times(1)).createHttpConsumer(Mockito.any(), Mockito.any(), Mockito.eq(HttpMethod.PATCH), Mockito.any());
    }

    @Test
    void shouldExtractAllEndpointInformation_whenScanClasspath() {
        Collection<HttpConsumer> actualEndpoints = (Collection) clazz.scanSenderAndReceiver("test.http.spring");

        Mockito.verify(endpointFactory, Mockito.times(1)).createHttpConsumer(Mockito.eq("test.http.spring.ConsumerEndpoints"), Mockito.eq("receivesAPostRequest"), Mockito.eq("POST"), Mockito.eq("a"));
    }
}