package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsConsumer;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsConsumers;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsProducer;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsProducers;
import com.hlag.tools.commvis.analyzer.model.EndpointFactory;
import com.hlag.tools.commvis.analyzer.model.ISenderReceiverCommunication;
import com.hlag.tools.commvis.analyzer.model.SqsConsumer;
import com.hlag.tools.commvis.analyzer.model.SqsProducer;
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
 * Scans the classpath for outgoing SQS events. Methods consuming these events have to be annotated with
 * {@code VisualizeSqsProducer}.
 *
 * @see VisualizeSqsConsumer
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SqsProducerScannerImpl implements IScannerService {
    private final EndpointFactory endpointFactory;

    @Override
    public Collection<ISenderReceiverCommunication> scanSenderAndReceiver(String rootPackageName) {
        Set<ISenderReceiverCommunication> endpoints = new HashSet<>();
        Reflections reflections = new Reflections(rootPackageName, Scanners.values());

        Set<Method> methods = reflections.get(MethodsAnnotated.with(VisualizeSqsProducer.class).as(Method.class));

        methods.forEach(m -> {
            VisualizeSqsProducer visualizeAnnotation = m.getDeclaredAnnotation(VisualizeSqsProducer.class);

            //as we found methods with the Annotation we must get a result here
            if (visualizeAnnotation == null) {
                log.error("Unable to find the annotation, but we found a method with the annotation. Make sure that the classpath contains all relevant libraries for your application.");
                throw new IllegalStateException("VisualizeSqsProducer annotation could not be read!");
            }

            endpoints.add(createSqsProducer(visualizeAnnotation, m));
        });

        methods = reflections.get(MethodsAnnotated.with(VisualizeSqsProducers.class).as(Method.class));
        methods.forEach(m -> {
            VisualizeSqsProducers visualizeAnnotations = m.getDeclaredAnnotation(VisualizeSqsProducers.class);

            //as we found methods with the Annotation we must get a result here
            if (visualizeAnnotations == null) {
                log.error("Unable to find the annotation, but we found a method with the annotation. Make sure that the classpath contains all relevant libraries for your application.");
                throw new IllegalStateException("VisualizeSqsProducers annotation could not be read!");
            }

            for (VisualizeSqsProducer visualizeAnnotation : visualizeAnnotations.value()) {
                endpoints.add(createSqsProducer(visualizeAnnotation, m));
            }
        });

        log.info("SQS producers found: {}", endpoints.size());

        return endpoints;
    }

    private SqsProducer createSqsProducer(VisualizeSqsProducer annotation, Method method) {
        return endpointFactory.createSqsProducer(method.getDeclaringClass().getCanonicalName(), method.getName(), annotation.queueName(), annotation.projectId());
    }
}
