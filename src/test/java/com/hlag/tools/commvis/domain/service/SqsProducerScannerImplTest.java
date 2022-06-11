package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.model.EndpointFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
        clazz.scanSenderAndReceiver("test.sqs.visualize");

        Mockito.verify(endpointFactory, Mockito.times(2)).createSqsProducer(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void shouldExtractAllSqsProducerInformation_whenScanClasspath() {
        clazz.scanSenderAndReceiver("test.sqs.visualize");

        Mockito.verify(endpointFactory).createSqsProducer(Mockito.eq("test.sqs.visualize.SqsProducer"), Mockito.eq("produceLocationSyncEvent"), Mockito.eq("locations"), Mockito.eq("myProject"));
    }
}