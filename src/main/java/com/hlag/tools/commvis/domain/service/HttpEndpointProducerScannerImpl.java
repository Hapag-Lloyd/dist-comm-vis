package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.annotation.VisualizeHttpsCall;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeHttpsCalls;
import com.hlag.tools.commvis.analyzer.model.HttpProducer;
import com.hlag.tools.commvis.analyzer.model.ISenderReceiverCommunication;
import com.hlag.tools.commvis.analyzer.service.IScannerService;
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
 * Scans the classpath for outgoing HTTP endpoints. Methods calling an HTTP endpoint have to be annotated with
 * {@link VisualizeHttpsCalls} or {@link VisualizeHttpsCall}.
 */
@Service
@Slf4j
public class HttpEndpointProducerScannerImpl implements IScannerService {
    @Override
    public Collection<ISenderReceiverCommunication> scanSenderAndReceiver(String rootPackageName) {
        Set<ISenderReceiverCommunication> endpoints = new HashSet<>();
        Reflections reflections = new Reflections(rootPackageName, Scanners.values());

        Set<Method> methods = reflections.get(MethodsAnnotated.with(VisualizeHttpsCall.class).as(Method.class));

        methods.forEach(m -> {
            VisualizeHttpsCall visualizeAnnotation = m.getDeclaredAnnotation(VisualizeHttpsCall.class);

            //as we found methods with the Annotation we must get a result here
            if (visualizeAnnotation == null) {
                log.error("Unable to find the annotation, but we found a method with the annotation. Make sure that the classpath contains all relevant libraries for your application.");
                throw new IllegalStateException("VisualizeHttpCall annotation could not be read!");
            }

            endpoints.add(createHttpProducer(visualizeAnnotation, m));
        });

        methods = reflections.get(MethodsAnnotated.with(VisualizeHttpsCalls.class).as(Method.class));
        methods.forEach(m -> {
            VisualizeHttpsCalls visualizeAnnotations = m.getDeclaredAnnotation(VisualizeHttpsCalls.class);

            //as we found methods with the Annotation we must get a result here
            if (visualizeAnnotations == null) {
                log.error("Unable to find the annotation, but we found a method with the annotation. Make sure that the classpath contains all relevant libraries for your application.");
                throw new IllegalStateException("VisualizeHttpCalls annotation could not be read!");
            }

            for (VisualizeHttpsCall visualizeAnnotation : visualizeAnnotations.value()) {
                endpoints.add(createHttpProducer(visualizeAnnotation, m));
            }
        });

        log.info("Outgoing HTTP calls found: {}", endpoints.size());

        return endpoints;
    }

    private HttpProducer createHttpProducer(VisualizeHttpsCall annotation, Method method) {
        return new HttpProducer(method.getDeclaringClass().getCanonicalName(), method.getName(), annotation.type(), annotation.path(), annotation.projectId());
    }
}
