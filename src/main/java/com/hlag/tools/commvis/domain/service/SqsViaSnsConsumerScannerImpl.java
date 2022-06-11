package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsConsumer;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsConsumers;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsViaSnsConsumer;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsViaSnsConsumers;
import com.hlag.tools.commvis.analyzer.model.EndpointFactory;
import com.hlag.tools.commvis.analyzer.model.ISenderReceiverCommunication;
import com.hlag.tools.commvis.analyzer.model.SqsConsumer;
import com.hlag.tools.commvis.analyzer.service.IScannerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.reflections.scanners.Scanners.MethodsAnnotated;

/**
 * Scans the classpath for incoming SQS events received from SNS. Methods consuming these events have to be annotated with
 * {@code VisualizeSqsViaSnsConsumer}.
 *
 * @see VisualizeSqsViaSnsConsumer
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SqsViaSnsConsumerScannerImpl implements IScannerService {
    private final EndpointFactory endpointFactory;

    @Override
    public Collection<ISenderReceiverCommunication> scanSenderAndReceiver(String rootPackageName) {
        Set<ISenderReceiverCommunication> endpoints = new HashSet<>();
        Reflections reflections = new Reflections(rootPackageName, Scanners.values());

        Set<Method> methods = reflections.get(MethodsAnnotated.with(VisualizeSqsViaSnsConsumer.class).as(Method.class));

        methods.forEach(m -> {
            VisualizeSqsViaSnsConsumer visualizeAnnotation = m.getDeclaredAnnotation(VisualizeSqsViaSnsConsumer.class);

            //as we found methods with the Annotation we must get a result here
            if (visualizeAnnotation == null) {
                log.error("Unable to find the annotation, but we found a method with the annotation. Make sure that the classpath contains all relevant libraries for your application.");
                throw new IllegalStateException("VisualizeSqsViaSnsConsumer annotation could not be read!");
            }

            endpoints.add(endpointFactory.createSqsViaSnsConsumer(visualizeAnnotation, m));
        });

        methods = reflections.get(MethodsAnnotated.with(VisualizeSqsViaSnsConsumers.class).as(Method.class));
        methods.forEach(m -> {
            VisualizeSqsViaSnsConsumers visualizeAnnotations = m.getDeclaredAnnotation(VisualizeSqsViaSnsConsumers.class);

            //as we found methods with the Annotation we must get a result here
            if (visualizeAnnotations == null) {
                log.error("Unable to find the annotation, but we found a method with the annotation. Make sure that the classpath contains all relevant libraries for your application.");
                throw new IllegalStateException("VisualizeSqsViaSnsConsumers annotation could not be read!");
            }

            for (VisualizeSqsViaSnsConsumer visualizeAnnotation : visualizeAnnotations.value()) {
                endpoints.add(endpointFactory.createSqsViaSnsConsumer(visualizeAnnotation, m));
            }
        });

        log.info("SQS consumers for SNS found: {}", endpoints.size());

        return endpoints;
    }
}
