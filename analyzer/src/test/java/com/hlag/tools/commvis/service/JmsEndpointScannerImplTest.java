package com.hlag.tools.commvis.service;

import com.hlag.tools.commvis.analyzer.model.JmsReceiver;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class JmsEndpointScannerImplTest {
    private JmsEndpointScannerImpl clazz;

    @BeforeEach
    void init() {
        this.clazz = new JmsEndpointScannerImpl();
    }

    @Test
    void shouldFindJmsReceivers_whenScanClasspath() {
        Collection<JmsReceiver> actualEndpoints = (Collection) clazz.scanSenderAndReceiver("test.jms");

        Assertions.assertThat(actualEndpoints).size().isEqualTo(1);
    }

    @Test
    void shouldExtractAllEndpointInformation_whenScanClasspath() {
        JmsReceiver expectedEndpoint = new JmsReceiver("test.jms.CustomerCancelledOrderReceiver", "javax.jms.Queue", "jms/customer/orderCancelled");

        Collection<JmsReceiver> actualEndpoints = (Collection) clazz.scanSenderAndReceiver("test.jms");

        assertThat(actualEndpoints).contains(expectedEndpoint);
    }
}