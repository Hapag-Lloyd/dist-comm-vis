package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.model.HttpReceiver;
import com.hlag.tools.commvis.analyzer.model.ISenderReceiverCommunication;
import com.hlag.tools.commvis.analyzer.service.IScannerService;
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
public class JaxRsEndpointScannerImpl implements IScannerService {
    @Override
    public Collection<ISenderReceiverCommunication> scanSenderAndReceiver(String rootPackageName) {
        Set<ISenderReceiverCommunication> endpoints = new HashSet<>();
        List<Class<? extends Annotation>> httpMethodsToScan = Arrays.asList(DELETE.class, GET.class, HEAD.class, OPTIONS.class, PATCH.class, POST.class, PUT.class);

        Reflections reflections = new Reflections(rootPackageName, Scanners.values());

        for (Class<? extends Annotation> httpMethod : httpMethodsToScan) {
            Set<Method> methods = reflections.get(MethodsAnnotated.with(httpMethod).as(Method.class));

            methods.forEach(m -> {
                Path pathOnMethod = m.getAnnotation(Path.class);
                Path pathOnClass = m.getDeclaringClass().getAnnotation(Path.class);

                String path = Stream.of(pathOnClass, pathOnMethod).filter(Objects::nonNull).map(p -> p.value()).collect(Collectors.joining("/"));

                endpoints.add(new HttpReceiver(m.getDeclaringClass().getCanonicalName(), m.getName(), httpMethod.getSimpleName(), path));
            });
        }

        return endpoints;
    }
}
