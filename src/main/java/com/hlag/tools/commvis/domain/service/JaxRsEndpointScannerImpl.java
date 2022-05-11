package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.annotation.VisualizeHttpsCall;
import com.hlag.tools.commvis.analyzer.annotation.VisualizeHttpsCalls;
import com.hlag.tools.commvis.analyzer.model.HttpConsumer;
import com.hlag.tools.commvis.analyzer.model.HttpProducer;
import com.hlag.tools.commvis.analyzer.model.ISenderReceiverCommunication;
import com.hlag.tools.commvis.analyzer.service.IScannerService;
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

        log.info("Outgoing Http(s) calls found: {}", endpoints.size());

        return endpoints;
    }

    private HttpProducer createHttpProducer(VisualizeHttpsCall annotation, Method method) {
        return new HttpProducer(method.getDeclaringClass().getCanonicalName(), method.getName(), annotation.type(), annotation.path(), annotation.projectId());
    }

    private Collection<ISenderReceiverCommunication> scanIncomingConnections(Reflections reflections) {
        Collection<ISenderReceiverCommunication> endpoints = new HashSet<>();
        List<Class<? extends Annotation>> httpMethodsToScan = Arrays.asList(DELETE.class, GET.class, HEAD.class, OPTIONS.class, PATCH.class, POST.class, PUT.class);

        for (Class<? extends Annotation> httpMethod : httpMethodsToScan) {
            Set<Method> methods = reflections.get(MethodsAnnotated.with(httpMethod).as(Method.class));

            methods.forEach(m -> {
                Path pathOnMethod = m.getAnnotation(Path.class);
                Path pathOnClass = m.getDeclaringClass().getAnnotation(Path.class);

                String path = Stream.of(pathOnClass, pathOnMethod).filter(Objects::nonNull).map(Path::value).collect(Collectors.joining("/"));

                endpoints.add(new HttpConsumer(m.getDeclaringClass().getCanonicalName(), m.getName(), httpMethod.getSimpleName(), path));
            });
        }

        log.info("Incoming JaxRs endpoints found: {}", endpoints.size());

        return endpoints;
    }
}
