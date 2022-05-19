package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.annotation.VisualizeHttpsCall;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsConsumer;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsConsumers;
import com.hlag.tools.commvis.analyzer.model.EndpointFactory;
import com.hlag.tools.commvis.analyzer.model.HttpProducer;
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
 * Scans the classpath for incoming SQS events. Methods consuming these events have to be annotated with
 * {@code VisualizeSqsConsumer}.
 *
 * @see com.hlag.tools.commvis.analyzer.annotation.VisualizeSqsConsumer
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SqsConsumerScannerImpl implements IScannerService {
    private final EndpointFactory endpointFactory;

    @Override
    public Collection<ISenderReceiverCommunication> scanSenderAndReceiver(String rootPackageName) {
        Set<ISenderReceiverCommunication> endpoints = new HashSet<>();
        Reflections reflections = new Reflections(rootPackageName, Scanners.values());

        Set<Method> methods = reflections.get(MethodsAnnotated.with(VisualizeSqsConsumer.class).as(Method.class));

        methods.forEach(m -> {
            VisualizeSqsConsumer visualizeAnnotation = m.getDeclaredAnnotation(VisualizeSqsConsumer.class);

            //as we found methods with the Annotation we must get a result here
            if (visualizeAnnotation == null) {
                log.error("Unable to find the annotation, but we found a method with the annotation. Make sure that the classpath contains all relevant libraries for your application.");
                throw new IllegalStateException("VisualizeHttpCall annotation could not be read!");
            }

            endpoints.add(createSqsConsumer(visualizeAnnotation, m));
        });

        methods = reflections.get(MethodsAnnotated.with(VisualizeSqsConsumers.class).as(Method.class));
        methods.forEach(m -> {
            VisualizeSqsConsumers visualizeAnnotations = m.getDeclaredAnnotation(VisualizeSqsConsumers.class);

            //as we found methods with the Annotation we must get a result here
            if (visualizeAnnotations == null) {
                log.error("Unable to find the annotation, but we found a method with the annotation. Make sure that the classpath contains all relevant libraries for your application.");
                throw new IllegalStateException("VisualizeHttpCalls annotation could not be read!");
            }

            for (VisualizeSqsConsumer visualizeAnnotation : visualizeAnnotations.value()) {
                endpoints.add(createSqsConsumer(visualizeAnnotation, m));
            }
        });

        log.info("Outgoing HTTP calls found: {}", endpoints.size());

        return endpoints;
    }

    private SqsConsumer createSqsConsumer(VisualizeSqsConsumer annotation, Method method) {
        return endpointFactory.createSqsReceiver(method.getDeclaringClass().getCanonicalName(), method.getName(), annotation.queueName());
    }
}
