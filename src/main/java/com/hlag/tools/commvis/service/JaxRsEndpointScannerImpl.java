package com.hlag.tools.commvis.service;

import com.hlag.tools.commvis.domain.model.HttpEndpoint;
import com.hlag.tools.commvis.domain.model.IEndpoint;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.reflections.scanners.Scanners.MethodsAnnotated;

@Service
public class JaxRsEndpointScannerImpl implements IEndpointScannerService {
    @Override
    public Set<IEndpoint> scanClasspath(String rootPackageName) {
        Set<IEndpoint> endpoints = new HashSet<>();
        List<Class<? extends Annotation>> httpMethodsToScan = Arrays.asList(DELETE.class, GET.class, HEAD.class, OPTIONS.class, PATCH.class, POST.class, PUT.class);

        Reflections reflections = new Reflections(rootPackageName, Scanners.values());

        for (Class<? extends Annotation> httpMethod : httpMethodsToScan) {
            Set<Method> methods = reflections.get(MethodsAnnotated.with(httpMethod).as(Method.class));

            methods.forEach(m -> endpoints.add(new HttpEndpoint(m.getDeclaringClass().getCanonicalName(), m.getName(), httpMethod.getSimpleName())));
        }

        return endpoints;
    }
}
