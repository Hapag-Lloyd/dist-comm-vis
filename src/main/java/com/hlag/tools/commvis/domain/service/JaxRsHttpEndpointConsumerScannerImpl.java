package com.hlag.tools.commvis.domain.service;

import com.hlag.tools.commvis.analyzer.model.HttpConsumer;
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
 * Scans the classpath for JaxRs HTTP endpoints.
 */
@Service
@Slf4j
public class JaxRsHttpEndpointConsumerScannerImpl implements IScannerService {
    @Override
    public Collection<ISenderReceiverCommunication> scanSenderAndReceiver(String rootPackageName) {
        Set<ISenderReceiverCommunication> endpoints = new HashSet<>();
        Reflections reflections = new Reflections(rootPackageName, Scanners.values());

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
