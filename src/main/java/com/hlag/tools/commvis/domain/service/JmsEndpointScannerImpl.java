package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.model.EndpointFactory;
import com.hlag.tools.commvis.analyzer.model.ISenderReceiverCommunication;
import com.hlag.tools.commvis.analyzer.model.JmsReceiver;
import com.hlag.tools.commvis.analyzer.service.IScannerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.springframework.stereotype.Service;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.reflections.scanners.Scanners.TypesAnnotated;

@Service
@Slf4j
@RequiredArgsConstructor
public class JmsEndpointScannerImpl implements IScannerService {
    private final EndpointFactory endpointFactory;

    @Override
    public Collection<ISenderReceiverCommunication> scanSenderAndReceiver(String rootPackageName) {
        Set<ISenderReceiverCommunication> endpoints = new HashSet<>();

        Reflections reflections = new Reflections(rootPackageName, Scanners.values());
        Set<Class<?>> classes = reflections.get(TypesAnnotated.with(MessageDriven.class).asClass());

        classes.forEach(c -> {
            MessageDriven[] annotation = c.getDeclaredAnnotationsByType(MessageDriven.class);

            //as we found classes with the Annotation we must get a result here
            if (annotation.length == 0) {
                log.error("Unable to find the annotation, but we found a class with the annotation. Make sure that the classpath contains all relevant libraries for your application.");
                throw new IllegalStateException("MessageDriven annotation could not be read!");
            }

            String destinationType = null;
            String destination = null;

            for (ActivationConfigProperty config : annotation[0].activationConfig()) {
                if ("destinationType".equals(config.propertyName())) {
                    destinationType = config.propertyValue();
                } else if ("destination".equals(config.propertyName())) {
                    destination = config.propertyValue();
                }
            }

            endpoints.add(endpointFactory.createJmsReceiver(c.getCanonicalName(), destinationType, destination));
        });

        return endpoints;
    }
}
