package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.model.EndpointFactory;
import com.hlag.tools.commvis.analyzer.model.JmsReceiver;
import com.hlag.tools.commvis.domain.service.JmsEndpointScannerImpl;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.HttpMethod;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class JmsEndpointScannerImplTest {
    @Mock
    private EndpointFactory endpointFactory;
    @InjectMocks
    private JmsEndpointScannerImpl clazz;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindJmsReceivers_whenScanClasspath() {
        Collection<JmsReceiver> actualEndpoints = (Collection) clazz.scanSenderAndReceiver("test.jms");

        Mockito.verify(endpointFactory, Mockito.times(1)).createJmsReceiver(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void shouldExtractAllEndpointInformation_whenScanClasspath() {
        Collection<JmsReceiver> actualEndpoints = (Collection) clazz.scanSenderAndReceiver("test.jms");

        Mockito.verify(endpointFactory, Mockito.times(1)).createJmsReceiver(Mockito.eq("test.jms.CustomerCancelledOrderReceiver"), Mockito.eq("javax.jms.Queue"), Mockito.eq("jms/customer/orderCancelled"));
    }
}