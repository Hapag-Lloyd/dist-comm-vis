package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.model.JmsReceiver;
import com.hlag.tools.commvis.domain.service.JmsEndpointScannerImpl;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
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
        Collection<JmsReceiver> actualEndpoints = (Collection) clazz.scanSenderAndReceiver("test.jms");

        assertThat(actualEndpoints).extracting(JmsReceiver::getClassName, JmsReceiver::getDestinationType, JmsReceiver::getDestination).contains(new Tuple("test.jms.CustomerCancelledOrderReceiver", "javax.jms.Queue", "jms/customer/orderCancelled"));
    }
}