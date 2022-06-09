package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.model.EndpointFactory;
import com.hlag.tools.commvis.analyzer.model.HttpProducer;
import com.hlag.tools.commvis.analyzer.model.ISenderReceiverCommunication;
import com.hlag.tools.commvis.analyzer.model.SqsProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collection;

class SqsProducerScannerImplTest {
    @Mock
    private EndpointFactory endpointFactory;

    @InjectMocks
    private SqsProducerScannerImpl clazz;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindAllSqsProducers_whenScanClasspath() {
        Collection<ISenderReceiverCommunication> actualEndpoints = clazz.scanSenderAndReceiver("test.sqs.visualize");

        Mockito.verify(endpointFactory, Mockito.times(3)).createSqsProducer(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void shouldExtractAllSqsProducerInformation_whenScanClasspath() {
        Collection<SqsProducer> actualEndpoints = (Collection) clazz.scanSenderAndReceiver("test.sqs.visualize");

        Mockito.verify(endpointFactory, Mockito.times(1)).createSqsProducer(Mockito.eq("test.sqs.visualize.SqsProducer"), Mockito.eq("produceLocationSyncEvent"), Mockito.eq("locations"), Mockito.eq("myProject"));
    }
}