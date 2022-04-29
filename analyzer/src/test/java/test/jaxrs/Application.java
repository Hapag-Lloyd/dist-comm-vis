package test.jaxrs;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Application extends javax.ws.rs.core.Application {
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(Endpoints.class);

        return classes;

    }
}
