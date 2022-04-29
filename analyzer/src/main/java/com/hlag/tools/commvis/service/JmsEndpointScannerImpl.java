package com.hlag.tools.commvis.service;

import com.hlag.tools.commvis.domain.model.IEndpoint;
import com.hlag.tools.commvis.domain.model.JmsEndpoint;
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
public class JmsEndpointScannerImpl implements IEndpointScannerService {
    @Override
    public Collection<IEndpoint> scanClasspath(String rootPackageName) {
        Set<IEndpoint> endpoints = new HashSet<>();

        Reflections reflections = new Reflections(rootPackageName, Scanners.values());
        Set<Class<?>> classes = reflections.get(TypesAnnotated.with(MessageDriven.class).asClass());

        classes.forEach(c -> {
            MessageDriven[] annotation = c.getDeclaredAnnotationsByType(MessageDriven.class);
            String destinationType = null;
            String destination = null;

            for (ActivationConfigProperty config : annotation[0].activationConfig()) {
                if ("destinationType".equals(config.propertyName())) {
                    destinationType = config.propertyValue();
                } else if ("destination".equals(config.propertyName())) {
                    destination = config.propertyValue();
                }
            }

            endpoints.add(new JmsEndpoint(c.getCanonicalName(), destinationType, destination));
        });

        return endpoints;
    }
}
