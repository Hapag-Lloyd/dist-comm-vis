package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.model.EndpointFactory;
import com.hlag.tools.commvis.analyzer.model.HttpProducer;
import com.hlag.tools.commvis.analyzer.model.ISenderReceiverCommunication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collection;

class SqsConsumerScannerImplTest {
    @Mock
    private EndpointFactory endpointFactory;

    @InjectMocks
    private SqsConsumerScannerImpl clazz;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindAllSqsConsunmers_whenScanClasspath() {
        Collection<ISenderReceiverCommunication> actualEndpoints = clazz.scanSenderAndReceiver("test.sqs.visualize");

        Mockito.verify(endpointFactory, Mockito.times(3)).createSqsReceiver(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void shouldExtractAllHttpProducerInformation_whenScanClasspath() {
        Collection<HttpProducer> actualEndpoints = (Collection) clazz.scanSenderAndReceiver("test.sqs.visualize");

        Mockito.verify(endpointFactory, Mockito.times(1)).createSqsReceiver(Mockito.eq("test.sqs.visualize.SqsReceiver"), Mockito.eq("receiveLocationSyncEvent"), Mockito.eq("locations"));
    }
}