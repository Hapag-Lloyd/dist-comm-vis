package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.annotation.VisualizeKafkaConsumer;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeKafkaConsumers;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsViaSnsConsumer;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsViaSnsConsumers;
import com.hlag.tools.commvis.analyzer.model.EndpointFactory;
import com.hlag.tools.commvis.analyzer.model.ISenderReceiverCommunication;
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
 * Scans the classpath for incoming Kafka events. Methods consuming these events have to be annotated with
 * {@code VisualizeKafkaConsumer}.
 *
 * @see VisualizeKafkaConsumer
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerScannerImpl implements IScannerService {
    private final EndpointFactory endpointFactory;

    @Override
    public Collection<ISenderReceiverCommunication> scanSenderAndReceiver(String rootPackageName) {
        Set<ISenderReceiverCommunication> endpoints = new HashSet<>();
        Reflections reflections = new Reflections(rootPackageName, Scanners.values());

        Set<Method> methods = reflections.get(MethodsAnnotated.with(VisualizeKafkaConsumer.class).as(Method.class));

        methods.forEach(m -> {
            VisualizeKafkaConsumer visualizeAnnotation = m.getDeclaredAnnotation(VisualizeKafkaConsumer.class);

            //as we found methods with the Annotation we must get a result here
            if (visualizeAnnotation == null) {
                log.error("Unable to find the annotation, but we found a method with the annotation. Make sure that the classpath contains all relevant libraries for your application.");
                throw new IllegalStateException("VisualizeKafkaConsumer annotation could not be read!");
            }

            endpoints.add(endpointFactory.createKafkaConsumer(visualizeAnnotation, m));
        });

        methods = reflections.get(MethodsAnnotated.with(VisualizeKafkaConsumers.class).as(Method.class));
        methods.forEach(m -> {
            VisualizeKafkaConsumers visualizeAnnotations = m.getDeclaredAnnotation(VisualizeKafkaConsumers.class);

            //as we found methods with the Annotation we must get a result here
            if (visualizeAnnotations == null) {
                log.error("Unable to find the annotation, but we found a method with the annotation. Make sure that the classpath contains all relevant libraries for your application.");
                throw new IllegalStateException("VisualizeKafkaConsumers annotation could not be read!");
            }

            for (VisualizeKafkaConsumer visualizeAnnotation : visualizeAnnotations.value()) {
                endpoints.add(endpointFactory.createKafkaConsumer(visualizeAnnotation, m));
            }
        });

        log.info("Kafka consumers found: {}", endpoints.size());

        return endpoints;
    }
}
