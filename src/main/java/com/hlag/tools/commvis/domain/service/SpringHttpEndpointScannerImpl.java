package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.model.EndpointFactory;
import com.hlag.tools.commvis.analyzer.model.HttpConsumer;
import com.hlag.tools.commvis.analyzer.model.ISenderReceiverCommunication;
import com.hlag.tools.commvis.analyzer.service.IScannerService;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.reflections.scanners.Scanners.MethodsAnnotated;

/**
 * Scans the classpath for Spring HTTP endpoints.
 */
@Service
@Slf4j
public class SpringHttpEndpointScannerImpl implements IScannerService {
    @Override
    public Collection<ISenderReceiverCommunication> scanSenderAndReceiver(String rootPackageName) {
        Set<ISenderReceiverCommunication> endpoints = new HashSet<>();
        Reflections reflections = new Reflections(rootPackageName, Scanners.values());

        Set<Method> methods = reflections.get(MethodsAnnotated.with(GetMapping.class).as(Method.class));

        methods.forEach(m -> {
            GetMapping mapping = m.getAnnotation(GetMapping.class);

            String path = mapping.path().length == 0 ? mapping.value()[0] : mapping.path()[0];

            //TODO HttpConsumer should allow multiple HTTP methods and multiple paths
            endpoints.add(EndpointFactory.get().createHttpConsumer(m.getDeclaringClass().getCanonicalName(), m.getName(), "GET", path));
        });

        methods = reflections.get(MethodsAnnotated.with(PutMapping.class).as(Method.class));

        methods.forEach(m -> {
            PutMapping mapping = m.getAnnotation(PutMapping.class);

            String path = mapping.path().length == 0 ? mapping.value()[0] : mapping.path()[0];

            //TODO HttpConsumer should allow multiple HTTP methods and multiple paths
            endpoints.add(EndpointFactory.get().createHttpConsumer(m.getDeclaringClass().getCanonicalName(), m.getName(), "PUT", path));
        });

        methods = reflections.get(MethodsAnnotated.with(PostMapping.class).as(Method.class));

        methods.forEach(m -> {
            PostMapping mapping = m.getAnnotation(PostMapping.class);

            String path = mapping.path().length == 0 ? mapping.value()[0] : mapping.path()[0];

            //TODO HttpConsumer should allow multiple HTTP methods and multiple paths
            endpoints.add(EndpointFactory.get().createHttpConsumer(m.getDeclaringClass().getCanonicalName(), m.getName(), "POST", path));
        });

        methods = reflections.get(MethodsAnnotated.with(PatchMapping.class).as(Method.class));

        methods.forEach(m -> {
            PatchMapping mapping = m.getAnnotation(PatchMapping.class);

            String path = mapping.path().length == 0 ? mapping.value()[0] : mapping.path()[0];

            //TODO HttpConsumer should allow multiple HTTP methods and multiple paths
            endpoints.add(EndpointFactory.get().createHttpConsumer(m.getDeclaringClass().getCanonicalName(), m.getName(), "PATCH", path));
        });

        methods = reflections.get(MethodsAnnotated.with(DeleteMapping.class).as(Method.class));

        methods.forEach(m -> {
            DeleteMapping mapping = m.getAnnotation(DeleteMapping.class);

            String path = mapping.path().length == 0 ? mapping.value()[0] : mapping.path()[0];

            //TODO HttpConsumer should allow multiple HTTP methods and multiple paths
            endpoints.add(EndpointFactory.get().createHttpConsumer(m.getDeclaringClass().getCanonicalName(), m.getName(), "DELETE", path));
        });

        methods = reflections.get(MethodsAnnotated.with(RequestMapping.class).as(Method.class));

        methods.forEach(m -> {
            RequestMapping mapping = m.getAnnotation(RequestMapping.class);

            String path = mapping.path().length == 0 ? mapping.value()[0] : mapping.path()[0];

            //TODO HttpConsumer should allow multiple HTTP methods and multiple paths
            endpoints.add(EndpointFactory.get().createHttpConsumer(m.getDeclaringClass().getCanonicalName(), m.getName(), mapping.method()[0].name(), path));
        });

        log.info("Incoming Spring HTTP endpoints found: {}", endpoints.size());

        return endpoints;
    }
}
