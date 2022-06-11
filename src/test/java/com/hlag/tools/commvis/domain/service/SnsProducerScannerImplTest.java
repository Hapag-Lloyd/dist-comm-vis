package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.model.EndpointFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class SnsProducerScannerImplTest {
    @Mock
    private EndpointFactory endpointFactory;

    @InjectMocks
    private SnsProducerScannerImpl clazz;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindAllSnsProducers_whenScanClasspath() {
        clazz.scanSenderAndReceiver("test.sns.visualize");

        Mockito.verify(endpointFactory).createSnsProducer(Mockito.any(), Mockito.any());
    }
}