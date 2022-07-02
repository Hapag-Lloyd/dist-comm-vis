package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.model.EndpointFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class KafkaProducerScannerImplTest {
    @Mock
    private EndpointFactory endpointFactory;

    @InjectMocks
    private KafkaProducerScannerImpl clazz;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindAllKafkaProducers_whenScanClasspath() {
        clazz.scanSenderAndReceiver("test.kafka.visualize");

        Mockito.verify(endpointFactory, Mockito.times(3)).createKafkaProducer(Mockito.any(), Mockito.any());
    }
}