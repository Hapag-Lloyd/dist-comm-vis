package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.model.EndpointFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class SqsViaSnsConsumerScannerImplTest {
    @Mock
    private EndpointFactory endpointFactory;

    @InjectMocks
    private SqsViaSnsConsumerScannerImpl clazz;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindAllSqsConsumers_whenScanClasspath() {
        clazz.scanSenderAndReceiver("test.sqs.visualize");

        Mockito.verify(endpointFactory, Mockito.times(3)).createSqsViaSnsConsumer(Mockito.any(), Mockito.any());
    }
}