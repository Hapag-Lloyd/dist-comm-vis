package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.annotation.VisualizeHttpsCall;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeHttpsCalls;
import com.hlag.tools.commvis.analyzer.model.HttpReceiver;
import com.hlag.tools.commvis.analyzer.model.ISenderReceiverCommunication;
import com.hlag.tools.commvis.analyzer.service.IScannerService;
import com.hlag.tools.commvis.domain.HttpProducer;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.reflections.scanners.Scanners.MethodsAnnotated;

/**
 * Scans the classpath for JMS endpoints.
 */
@Service
@Slf4j
public class JaxRsEndpointScannerImpl implements IScannerService {
    @Override
    public Collection<ISenderReceiverCommunication> scanSenderAndReceiver(String rootPackageName) {
        Set<ISenderReceiverCommunication> endpoints = new HashSet<>();
        Reflections reflections = new Reflections(rootPackageName, Scanners.values());

        endpoints.addAll(scanIncomingConnections(reflections));
        endpoints.addAll(scanOutgoingConnections(reflections));

        return endpoints;
    }

    private Collection<? extends ISenderReceiverCommunication> scanOutgoingConnections(Reflections reflections) {
        Collection<ISenderReceiverCommunication> endpoints = new HashSet<>();

        Set<Method> methods = reflections.get(MethodsAnnotated.with(VisualizeHttpsCall.class).as(Method.class));

        methods.forEach(m -> {
            VisualizeHttpsCall visualizeAnnotation = m.getDeclaredAnnotation(VisualizeHttpsCall.class);

            endpoints.add(createHttpProducer(visualizeAnnotation));
        });

        methods = reflections.get(MethodsAnnotated.with(VisualizeHttpsCalls.class).as(Method.class));
        methods.forEach(m -> {
            VisualizeHttpsCalls visualizeAnnotations = m.getDeclaredAnnotation(VisualizeHttpsCalls.class);

            for (VisualizeHttpsCall visualizeAnnotation : visualizeAnnotations.value()) {
                endpoints.add(createHttpProducer(visualizeAnnotation));
            }
        });

        log.info("Outgoing Http(s) calls found: {}", endpoints.size());

        return endpoints;
    }

    private HttpProducer createHttpProducer(VisualizeHttpsCall annotation) {
        return new HttpProducer(annotation.method().value(), annotation.path(), annotation.projectId());
    }

    private Collection<ISenderReceiverCommunication> scanIncomingConnections(Reflections reflections) {
        Collection<ISenderReceiverCommunication> endpoints = new HashSet<>();
        List<Class<? extends Annotation>> httpMethodsToScan = Arrays.asList(DELETE.class, GET.class, HEAD.class, OPTIONS.class, PATCH.class, POST.class, PUT.class);

        for (Class<? extends Annotation> httpMethod : httpMethodsToScan) {
            Set<Method> methods = reflections.get(MethodsAnnotated.with(httpMethod).as(Method.class));

            methods.forEach(m -> {
                Path pathOnMethod = m.getAnnotation(Path.class);
                Path pathOnClass = m.getDeclaringClass().getAnnotation(Path.class);

                String path = Stream.of(pathOnClass, pathOnMethod).filter(Objects::nonNull).map(p -> p.value()).collect(Collectors.joining("/"));

                endpoints.add(new HttpReceiver(m.getDeclaringClass().getCanonicalName(), m.getName(), httpMethod.getSimpleName(), path));
            });
        }

        log.info("Incoming JaxRs endpoints found: {}", endpoints.size());

        return endpoints;
    }
}
