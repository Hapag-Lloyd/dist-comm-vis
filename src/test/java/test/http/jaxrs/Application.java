package test.http.jaxrs;

import javax.ws.rs.Path;
import java.util.HashSet;
import java.util.Set;

@Path("/api")
public class Application extends javax.ws.rs.core.Application {
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(ConsumerEndpoints.class);

        return classes;
    }
}
