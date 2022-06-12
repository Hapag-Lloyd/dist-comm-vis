package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.model.EndpointFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class KafkaConsumerScannerImplTest {
    @Mock
    private EndpointFactory endpointFactory;

    @InjectMocks
    private KafkaConsumerScannerImpl clazz;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindAllKafkaConsumers_whenScanClasspath() {
        clazz.scanSenderAndReceiver("test.kafka.visualize");

        Mockito.verify(endpointFactory, Mockito.times(3)).createKafkaConsumer(Mockito.any(), Mockito.any());
    }
}