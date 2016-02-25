package net.cpollet.itinerants.rest.v1.jersey.filters.configurer;

import javax.ws.rs.container.ContainerRequestContext;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Christophe Pollet
 */
public class VersionConfigurerChain {
    private final List<VersionConfigurer> configurers;
    private ThreadLocal<Iterator<VersionConfigurer>> it = new ThreadLocal<>();

    public VersionConfigurerChain(VersionConfigurer... configurers) {
        this.configurers = Arrays.asList(configurers);
    }

    public void before() {
        it.set(configurers.iterator());
    }

    public void configure(ContainerRequestContext requestContext) {
        if (it.get().hasNext()) {
            it.get().next().configure(requestContext, this);
        }
    }

    public void after() {
        it.remove();
    }
}
