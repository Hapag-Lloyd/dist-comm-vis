package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.annotation.VisualizeKafkaProducer;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeKafkaProducers;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeSnsProducer;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeSnsProducers;
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
 * Scans the classpath for outgoing Kafka events. Methods consuming these events have to be annotated with
 * {@code VisualizeKafkaProducer}.
 *
 * @see VisualizeKafkaProducer
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerScannerImpl implements IScannerService {
    private final EndpointFactory endpointFactory;

    @Override
    public Collection<ISenderReceiverCommunication> scanSenderAndReceiver(String rootPackageName) {
        Set<ISenderReceiverCommunication> endpoints = new HashSet<>();
        Reflections reflections = new Reflections(rootPackageName, Scanners.values());

        Set<Method> methods = reflections.get(MethodsAnnotated.with(VisualizeKafkaProducer.class).as(Method.class));

        methods.forEach(m -> {
            VisualizeKafkaProducer visualizeAnnotation = m.getDeclaredAnnotation(VisualizeKafkaProducer.class);

            //as we found methods with the Annotation we must get a result here
            if (visualizeAnnotation == null) {
                log.error("Unable to find the annotation, but we found a method with the annotation. Make sure that the classpath contains all relevant libraries for your application.");
                throw new IllegalStateException("VisualizeKafkaProducer annotation could not be read!");
            }

            endpoints.add(endpointFactory.createKafkaProducer(visualizeAnnotation, m));
        });

        methods = reflections.get(MethodsAnnotated.with(VisualizeKafkaProducers.class).as(Method.class));
        methods.forEach(m -> {
            VisualizeKafkaProducers visualizeAnnotations = m.getDeclaredAnnotation(VisualizeKafkaProducers.class);

            //as we found methods with the Annotation we must get a result here
            if (visualizeAnnotations == null) {
                log.error("Unable to find the annotation, but we found a method with the annotation. Make sure that the classpath contains all relevant libraries for your application.");
                throw new IllegalStateException("VisualizeKafkaProducers annotation could not be read!");
            }

            for (VisualizeKafkaProducer visualizeAnnotation : visualizeAnnotations.value()) {
                endpoints.add(endpointFactory.createKafkaProducer(visualizeAnnotation, m));
            }
        });

        log.info("Kafka producers found: {}", endpoints.size());

        return endpoints;
    }
}
